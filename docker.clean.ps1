# Удаление всех образов Докера
docker ps -a -q | % { docker rm $_ }
docker images -q | % { docker rmi -f $_ }