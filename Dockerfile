# Используем базовый образ
FROM ruby:3.1

  # Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Другие инструкции по настройке вашего образа
RUN apt-get update
RUN apt-get install -y docker.io
