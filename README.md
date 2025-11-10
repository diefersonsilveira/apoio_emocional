# Apoio Emocional

Aplicativo Android de apoio ao bem‑estar emocional, com quizzes, exercícios de respiração, diário do humor, chatbot e conteúdos educativos.

**Application ID:** `com.project.apoioemocional`

**SDK:** compileSdk 36, targetSdk 36, minSdk 30

**Activities principais detectadas:**

- `SplashActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/SplashActivity.kt`
- `LoginActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/auth/LoginActivity.kt`
- `RegisterActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/auth/RegisterActivity.kt`
- `BreathingActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/breathing/BreathingActivity.kt`
- `ChatActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/chatbot/ChatActivity.kt`
- `ConteudosActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/conteudo/ConteudoActivity.kt`
- `HomeActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/home/HomeActivity.kt`
- `MoodHistoryActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/moodjournal/MoodHistoryActivity.kt`
- `MoodJournalActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/moodjournal/MoodJournalActivity.kt`
- `CarePlanActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/psychologist/CarePlanActivity.kt`
- `QuizActivity` — `apoio_emocional-main/app/src/main/java/com/project/apoioemocional/quiz/QuizActivity.kt`

**Activities no Manifest:**

- `.content.ConteudosActivity`
- `.quiz.QuizActivity`
- `.home.HomeActivity`
- `.auth.RegisterActivity`
- `.auth.LoginActivity`
- `.SplashActivity`
- `.chatbot.ChatActivity`
- `.psychologist.CarePlanActivity`
- `.breathing.BreathingActivity`
- `.moodjournal.MoodJournalActivity`
- `.moodjournal.MoodHistoryActivity`

## Recursos

- Autenticação com Firebase (Login/Logout)
- Tela inicial com saudações dinâmicas
- Quiz de bem-estar (ansiedade, depressão, meditação)
- Respiração 4-7-8
- Diário do humor (Mood Journal)
- Chatbot de apoio
- Conteúdos educativos
- Planos de cuidado (psicologia)

## Pré‑requisitos

- Android Studio (Giraffe ou superior)
- JDK 17 (ou o recomendado pelo projeto)
- Conta Firebase e `google-services.json` configurado no módulo `app`
- Gradle Wrapper incluído no projeto

## Configuração do Firebase

1. Crie um projeto no Firebase.
2. Adicione um app Android com o mesmo `applicationId`.
3. Baixe o `google-services.json` e coloque em `app/`.
4. Ative os produtos necessários (ex.: Authentication, Firestore/Realtime Database se usado).

## Como rodar

```bash
./gradlew assembleDebug
# ou pelo Android Studio: Run ▶
```

## Estrutura do projeto (resumo)

```

└─ apoio_emocional-main
   ├─ app
   │  ├─ build.gradle.kts
   │  ├─ google-services.json
   │  ├─ proguard-rules.pro
   │  └─ src
   │     ├─ androidTest
   │     │  └─ java
   │     │     └─ com
   │     │        └─ project
   │     │           └─ apoioemocional
   │     │              └─ ExampleInstrumentedTest.kt
   │     ├─ main
   │     │  ├─ AndroidManifest.xml
   │     │  ├─ java
   │     │  │  └─ com
   │     │  │     └─ project
   │     │  │        └─ apoioemocional
   │     │  │           ├─ SplashActivity.kt
   │     │  │           ├─ auth
   │     │  │           │  ├─ LoginActivity.kt
   │     │  │           │  └─ RegisterActivity.kt
   │     │  │           ├─ breathing
   │     │  │           │  └─ BreathingActivity.kt
   │     │  │           ├─ chatbot
   │     │  │           │  ├─ ChatActivity.kt
   │     │  │           │  ├─ Message.kt
   │     │  │           │  └─ MessageAdapter.kt
   │     │  │           ├─ conteudo
   │     │  │           │  └─ ConteudoActivity.kt
   │     │  │           ├─ home
   │     │  │           │  └─ HomeActivity.kt
   │     │  │           ├─ moodjournal
   │     │  │           │  ├─ MoodHistoryActivity.kt
   │     │  │           │  └─ MoodJournalActivity.kt
   │     │  │           ├─ psychologist
   │     │  │           │  ├─ CarePlanActivity.kt
   │     │  │           │  ├─ Psicologo.kt
   │     │  │           │  └─ PsicologoAdapter.kt
   │     │  │           └─ quiz
   │     │  │              └─ QuizActivity.kt
   │     │  └─ res
   │     │     ├─ drawable
   │     │     │  ├─ bg_circle_breathing.xml
   │     │     │  ├─ bg_home_header.xml
   │     │     │  ├─ bg_label_pill.xml
   │     │     │  ├─ bg_login.png
   │     │     │  ├─ exemploconteudo1.png
   │     │     │  ├─ exemploconteudo2.png
   │     │     │  ├─ exemploconteudo3.png
   │     │     │  ├─ ic_arrow_back.xml
   │     │     │  ├─ ic_chat_24.xml
   │     │     │  ├─ ic_launcher_background.xml
   │     │     │  ├─ ic_launcher_foreground.xml
   │     │     │  ├─ ic_mood_angry.png
   │     │     │  ├─ ic_mood_anxious.png
   │     │     │  ├─ ic_mood_calm.png
   │     │     │  ├─ ic_mood_happy.png
   │     │     │  ├─ ic_mood_sad.png
   │     │     │  ├─ ic_send.xml
   │     │     │  └─ logo.png
   │     │     ├─ layout
   │     │     │  ├─ activity_breathing.xml
   │     │     │  ├─ activity_care_plan.xml
   │     │     │  ├─ activity_chatbot.xml
   │     │     │  ├─ activity_conteudo.xml
   │     │     │  ├─ activity_home.xml
   │     │     │  ├─ activity_login.xml
   │     │     │  ├─ activity_mood_history.xml
   │     │     │  ├─ activity_mood_journal.xml
   │     │     │  ├─ activity_quiz.xml
   │     │     │  ├─ activity_register.xml
   │     │     │  └─ item_psicologo.xml
   │     │     ├─ mipmap-anydpi
   │     │     │  ├─ ic_launcher.xml
   │     │     │  └─ ic_launcher_round.xml
   │     │     ├─ mipmap-hdpi
   │     │     │  ├─ ic_launcher.webp
   │     │     │  └─ ic_launcher_round.webp
   │     │     ├─ mipmap-mdpi
   │     │     │  ├─ ic_launcher.webp
   │     │     │  └─ ic_launcher_round.webp
   │     │     ├─ mipmap-xhdpi
   │     │     │  ├─ ic_launcher.webp
   │     │     │  └─ ic_launcher_round.webp
   │     │     ├─ mipmap-xxhdpi
   │     │     │  ├─ ic_launcher.webp
   │     │     │  └─ ic_launcher_round.webp
   │     │     ├─ mipmap-xxxhdpi
   │     │     │  ├─ ic_launcher.webp
   │     │     │  └─ ic_launcher_round.webp
   │     │     ├─ values
   │     │     │  ├─ arrays.xml
   │     │     │  ├─ colors.xml
   │     │     │  ├─ string.xml
   │     │     │  ├─ strings.xml
   │     │     │  └─ themes.xml
   │     │     ├─ values-night
   │     │     │  └─ themes.xml
   │     │     └─ xml
   │     │        ├─ backup_rules.xml
   │     │        └─ data_extraction_rules.xml
   │     └─ test
   │        └─ java
   │           └─ com
   │              └─ project
   │                 └─ apoioemocional
   │                    └─ ExampleUnitTest.kt
   ├─ build.gradle.kts
   ├─ gradle
   │  ├─ libs.versions.toml
   │  └─ wrapper
   │     ├─ gradle-wrapper.jar
   │     └─ gradle-wrapper.properties
   ├─ gradle.properties
   ├─ gradlew
   ├─ gradlew.bat
   └─ settings.gradle.kts

```

## Dependências (trechos do build.gradle do app)

```gradle
implementation(platform("com.google.firebase:firebase-bom:34.3.0"))
implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
implementation("com.google.firebase:firebase-database-ktx:21.0.0")
implementation("androidx.recyclerview:recyclerview:1.4.0")
implementation("androidx.cardview:cardview:1.0.0")
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.appcompat)
implementation(libs.material)
implementation(libs.androidx.activity)
implementation(libs.androidx.constraintlayout)
implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)
```

## Licença

Este projeto é de uso acadêmico/educacional. Ajuste a licença conforme necessário.
