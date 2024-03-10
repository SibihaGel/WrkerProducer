# Используем базовый образ
FROM ruby:3.1

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Устанавливаем необходимые пакеты для установки Docker
RUN apt-get update && apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release

# Добавляем официальный ключ Docker
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Устанавливаем стабильную версию Docker
RUN echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
RUN apt-get update && apt-get install -y docker-ce docker-ce-cli containerd.io

