import com.cloudbees.hudson.plugins.folder.*
import hudson.plugins.git.GitSCM
import hudson.plugins.git.UserRemoteConfig
import hudson.plugins.gradle.GradleInstallation
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import org.jenkinsci.plugins.workflow.flow.FlowDefinition

Jenkins jenkins = Jenkins.instance

// Gradle
def gradleInstallation = new GradleInstallation("gradle-4.9", "", null)
def gradleInstallationDescriptor = jenkins.getDescriptorByType(hudson.plugins.gradle.GradleInstallation.DescriptorImpl)
gradleInstallationDescriptor.setInstallations(gradleInstallation)
gradleInstallationDescriptor.save()

// NodeJs
def nodeJsInstallation = new jenkins.plugins.nodejs.tools.NodeJSInstallation("nodejs-8.12.0", "", null)
def nodeJsInstallationDescriptor = jenkins.getDescriptorByType(jenkins.plugins.nodejs.tools.NodeJSInstallation.DescriptorImpl)
nodeJsInstallationDescriptor.setInstallations(nodeJsInstallation)
nodeJsInstallationDescriptor.save()

// Ansible
def ansibleInstallation = new org.jenkinsci.plugins.ansible.AnsibleInstallation("ansible-2.5.5", "/usr/bin", null)
def ansibleInstallationDescriptor = jenkins.getDescriptorByType(org.jenkinsci.plugins.ansible.AnsibleInstallation.DescriptorImpl)
ansibleInstallationDescriptor.setInstallations(ansibleInstallation)
ansibleInstallationDescriptor.save()

// Folder
String folderName = "vg"
def folder = jenkins.getItem(folderName)
if (folder == null) {
    folder = jenkins.createProject(Folder.class, folderName)
}

// Jobs
UserRemoteConfig userRemoteConfig = new UserRemoteConfig("https://github.com/gurv/vg-ops.git", "vg-ops", null, null)
GitSCM scm = new GitSCM([userRemoteConfig], null, false, null, null, null, [])
[
    'project',
    'middle',
    'ops',
    'core',
    'examples',
    'doc',
    'web',
].each { jobName ->
    FlowDefinition flowDefinition = (FlowDefinition) new CpsScmFlowDefinition(scm, "jenkins/job/vg-${jobName}.jenkinsfile")
    Object job = folder.getItem(jobName)
    if (job == null) {
        job = folder.createProject(WorkflowJob, jobName)
    }
    job.setDefinition(flowDefinition)
    job.save()
}