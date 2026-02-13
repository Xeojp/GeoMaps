# Навигатор с онлайн картами для Android (текущая версия v0.1)

Это приложение навигатора с онлайн картами для Android, разработанное с использованием последний технологий и подходов Android-разработки.

## Что включает в себя это приложние (или проект)

- Интерактивная карта с возможностью масштабирования и перемещения
- Поиск мест и адресов
- Построение маршрутов между точками
- Информационные панели с деталями маршрута
- Возможность сохранения избранных мест
- Поддержка геолокации

## Виджеты и компоненты

- **Карта** - основной компонент для отображения географических данных
- **Панель поиска** - для ввода и отображения результатов поиска
- **Кнопки управления** - для построения маршрутов и отображения текущего местоположения
- **Список результатов** - для отображения найденных мест
- **Панель маршрута** - для ввода начальной и конечной точки маршрута
- **Кнопки действий** - для управления функциями приложения

## Технологии

- **Kotlin** - основной язык программирования
- **Jetpack Compose** - для создания современного UI
- **Google Maps SDK for Android** - для интеграции с картами
- **Room Database** - для локального хранения данных
- **ViewModel** - для управления состоянием UI
- **Coroutines** - для асинхронного программирования

## Архитектура

Приложение построено с использованием архитектурного шаблона MVVM (Model-View-ViewModel):

## Структура проекта

```
app/src/main/java/com/example/navigationapp/
├── MainActivity.kt              # Главная активность приложения
├── CompleteMapScreen.kt         # Основной экран с картой и полным функционалом
├── SearchViewModel.kt           # ViewModel для поиска мест
├── RouteViewModel.kt            # ViewModel для построения маршрутов
├── FavoritesViewModel.kt        # ViewModel для управления избранными местами
├── FavoritesRepository.kt       # Репозиторий для работы с избранными местами
├── NavigationApplication.kt     # Класс приложения для инициализации базы данных
├── Favorite.kt                  # Модель данных для избранного места
├── FavoriteDao.kt               # DAO для работы с избранными местами
├── FavoritesDatabase.kt         # Класс базы данных Room
├── Converters.kt                # Конвертеры типов для Room
└── ...
```

## Зависимости

Основные зависимости проекта:

```gradle
implementation 'androidx.core:core-ktx:1.10.1'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
implementation 'androidx.activity:activity-compose:1.7.2'
implementation 'androidx.compose.ui:ui:1.4.3'
implementation 'androidx.compose.ui:ui-graphics:1.4.3'
implementation 'androidx.compose.ui:ui-tooling-preview:1.4.3'
implementation 'androidx.compose.material3:material3:1.1.1'
implementation 'com.google.android.gms:play-services-maps:18.1.0'
implementation 'com.google.android.gms:play-services-location:21.0.1'
implementation 'androidx.navigation:navigation-compose:2.5.3'
implementation 'androidx.room:room-runtime:2.5.0'
implementation 'androidx.room:room-ktx:2.5.0'
annotationProcessor 'androidx.room:room-compiler:2.5.0'
implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1'
```

## Настройка проекта

1. Вы должнв убедиться, что у вас установлены последние версии Android Studio и SDK
2. Получите API-ключ для Google Maps
3. Добавьте ключ в переменные окружения или в конфигурацию сборки
4. Синхронизируйте проект с Gradle

## Запуск приложения

1. Подключите устройство с Android 7.0 (API level 24) или выше
2. Включите режим разработчика и отладку по USB
3. Запустите приложение из Android Studio

## Лицензия


Проект является учебным и может быть использован свободно для ОБРАЗОВАТЕЛЬНЫХ целей.
