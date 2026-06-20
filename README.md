# LyraApp - Online/Offline Müzik Çalar Uygulaması

LyraApp, Android platformu için Jetpack Compose kullanılarak geliştirilen modern bir çevrimiçi ve çevrimdışı müzik çalar uygulamasıdır. Proje, temiz kod prensiplerini ve modern Android mimari standartlarını takip edecek şekilde yapılandırılmıştır.

---

## Teknoloji Yığını

*   **Platform:** Android (Minimum SDK: 26, Target SDK: 35)
*   **Programlama Dili:** Kotlin
*   **Arayüz (UI) Çerçevesi:** Jetpack Compose (Material Design 3)
*   **Bağımlılık Enjeksiyonu (DI):** Dagger Hilt
*   **Navigasyon:** Jetpack Compose Navigation
*   **Eşzamanlılık (Concurrency):** Kotlin Coroutines & Flow
*   **Veri Katmanı:** Mock/Fake veri depoları (Repository) üzerinden simülasyon (Backend RESTful API entegrasyonu hazırlık aşamasındadır)

---

## Mimari Yapı

Proje, tek yönlü veri akışını (Unidirectional Data Flow) temel alan **MVI (Model-View-Intent)** mimarisini kullanmaktadır. Bu mimarideki temel bileşenler şunlardır:

*   **UiState:** Ekranın herhangi bir andaki durumunu temsil eden değişmez (immutable) veri sınıfıdır.
*   **Intent:** Kullanıcının ekranda gerçekleştirdiği eylemleri veya tetiklemek istediği işlemleri temsil eden mühürlü arayüzdür (sealed interface).
*   **Effect:** Ekranda bir kez gösterilip kaybolması gereken olayları (hata mesajları, yönlendirmeler vb.) temsil eden mühürlü arayüzdür (sealed interface).

---

## Tasarım Sistemi

Uygulamanın arayüz tasarımı Material Design 3 standartlarına dayanmakta olup, özelleştirilmiş bir renk ve tipografi sistemine sahiptir:

1.  **Renk Sistemi:** `docs/design/00-color-system.md` dosyasında detaylandırıldığı üzere, tüm renk tanımları `MaterialTheme.colorScheme` üzerinden okunur. Dinamik renk özelliği devre dışı bırakılmıştır; marka paleti sabit kalmaktadır.
2.  **Tipografi Sistemi:** `docs/design/01-typography-system.md` dosyasında açıklandığı üzere, uygulama genelinde font ailesi olarak Roboto kullanılmaktadır. Tüm metin stilleri `MaterialTheme.typography` üzerinden atanmaktadır.

---

## Proje Klasör Yapısı

```text
lyraapp-gygy5/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/turkcell/lyraapp/
│           │   ├── data/          # Veri modelleri ve repository implementasyonları
│           │   ├── di/            # Hilt modülleri (bağımlılık enjeksiyonları)
│           │   ├── ui/            # UI katmanı (Ekranlar, temalar, özel simgeler ve navigasyon)
│           │   │   ├── auth/      # Giriş (Login) ve Kayıt (Register) ekranları
│           │   │   ├── home/      # Ana sayfa akışı ve MVI bileşenleri
│           │   │   ├── icons/     # Uygulamaya özel simgeler (LyraIcons)
│           │   │   ├── navigation/# Navigasyon host ve bottom bar bileşenleri
│           │   │   └── theme/     # Renk, tipografi ve tema tanımları (Color, Type, Theme)
│           │   ├── LyraApplication.kt # Uygulama sınıfı (@HiltAndroidApp)
│           │   └── MainActivity.kt    # Ana aktivite (@AndroidEntryPoint)
│           └── res/               # Android kaynak dosyaları (fontlar, görsel öğeler)
├── docs/                          # Proje dokümantasyonu ve mimari kararlar
│   ├── architecture/              # MVI ve ViewModel kuralları
│   ├── design/                    # Renk ve tipografi sistem dökümanları
│   └── decisions.md               # Teknik karar kayıtları
└── agents.md                      # Geliştirici kuralları ve çalışma prensipleri
```

---

## Geliştirme Kuralları ve Çalışma Prensipleri

Projeye katkıda bulunan tüm geliştiricilerin (insan veya yapay zeka) uymakla yükümlü olduğu kurallar `agents.md` dosyasında tanımlanmıştır. Temel kurallar şunlardır:

1.  **Tek Seferde Dosya Limiti:** Herhangi bir görev kapsamında tek seferde en fazla 5 birbiriyle ilişkili dosya üzerinde değişiklik yapılabilir. Daha büyük işlemler bağımsız adımlara bölünmelidir.
2.  **Planlama Önceliği:** Kodlama işlemine başlanmadan önce değişecek dosyalar listelenmeli, varsa yeni bağımlılıklar belirtilmeli ve plan sunulup onay alınmalıdır.
3.  **Arayüz Standartları:** Bileşenler içinde ham renk (`Color(0xFF...)`) veya ham tipografi (`TextStyle(...)`) tanımlanamaz. Değerler her zaman `MaterialTheme` üzerinden okunmalıdır.
