# Алгоритм генерации фрактального пламени

Проект из [Академии Бэкенда 2024][course-url]

## Установка

#### 1. Скачивание [Docker](https://www.docker.com/get-started)

*Проверка работы*
```bash
docker -v
```

```bash
docker ps
```


#### 2. Клонирование репозитория

```bash
git clone https://github.com/RyaRom/backend_academy_2024_project_4-java-RyaRom
```

```bash
cd backend_academy_2024_project_4-java-RyaRom
```

#### 3. Запуск

```bash
docker-compose up --build
```

#### 4. Сайт доступен по адресу

```
http://localhost:3000
```

#### 5. Для лучшей генерации можно увеличивать параметры точек и итераций
*Оптимальный варант*:
- ~20000 точек
- ~2000 итераций
- любой параметр симметрии
- ~1500-2000 ширина
- ~1500-2000 высота
- 1.77 глубина
- 2.2 гамма коэфицент
- 1-4 случайные функции


[course-url]: https://edu.tinkoff.ru/all-activities/courses/870efa9d-7067-4713-97ae-7db256b73eab
