# Wikipedia Search

Консольное приложение на Java для поиска статей в русскоязычной Википедии с использованием официального MediaWiki API.

## Возможности

- Поиск статей в русскоязычной Википедии по ключевым словам.
- Получение результатов через MediaWiki API.
- Обработка JSON-ответов с помощью библиотеки Gson.
- Вывод найденных статей в консоль.
- Открытие выбранной статьи в браузере.
- Поддержка русского языка и кодировки UTF-8.

## Технологии

- **Java 21** — язык программирования.
- **Gson 2.13.2** — обработка JSON.
- **MediaWiki API** — получение результатов поиска.
- **HTTP Client** — отправка HTTP-запросов.
- **VS Code** — среда разработки.

## Требования

Перед запуском необходимо установить:

- JDK 21 или выше.
- Git (для клонирования репозитория).
- Терминал с поддержкой UTF-8.

Библиотека Gson уже находится в проекте, поэтому устанавливать её отдельно не требуется.

## Установка и запуск

### 1. Клонирование репозитория

```bash
git clone <URL-репозитория>
```

Перейдите в папку проекта:

```bash
cd Java_homework
```

### 2. Компиляция

**Windows (PowerShell):**

```powershell
javac -encoding UTF-8 -cp "src/Homework2/lib/gson-2.13.2.jar" -d out src/Homework2/wikiSearch/*.java
```

### 3. Запуск

**Windows (PowerShell):**

```powershell
java -cp "out;src/Homework2/lib/gson-2.13.2.jar" Homework2.wikiSearch.Main
```

**Linux / macOS:**

```bash
javac -encoding UTF-8 -cp "src/Homework2/lib/gson-2.13.2.jar" -d out src/Homework2/wikiSearch/*.java
```

```bash
java -cp "out:src/Homework2/lib/gson-2.13.2.jar" Homework2.wikiSearch.Main
```

## Кодировка

Исходные файлы проекта должны быть сохранены в UTF-8.

Для корректного отображения русских символов в Windows PowerShell можно использовать:

```powershell
chcp 65001
[Console]::InputEncoding = [System.Text.UTF8Encoding]::new()
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new()
```

## Использование

1. Запустите приложение.
2. Введите поисковый запрос.
3. Дождитесь получения результатов из Википедии.
4. Выберите нужную статью по её номеру.
5. Приложение откроет выбранную статью в браузере.

## Источник данных

Для поиска используется API русскоязычной Википедии:

https://ru.wikipedia.org/w/api.php

Проект предназначен для учебных целей.
