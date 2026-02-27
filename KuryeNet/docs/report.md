# KuryeNet Teknik Analiz Raporu

Tarih: 2026-02-03

## 1) Özet
KuryeNet; Spring Boot 2.7.17 üzerinde çalışan, kargo/kurye operasyonlarını yöneten monolitik bir REST API uygulamasıdır. Uygulama; adres, müşteri, kurye, sağlayıcı, araç ve sipariş yönetimi gibi çekirdek domain’leri; ayrıca kullanıcı/rol tabanlı kimlik doğrulama ve harici servis (Google Maps, OpenWeatherMap, ML servis) entegrasyonlarını içerir. Rapor; mimari yapı, veri modeli, API uçları, güvenlik, harici entegrasyonlar, testler ve iyileştirme önerilerini kapsamlı şekilde özetler.

## 2) Mimari ve Katmanlar
- **Giriş noktası**: `src/main/java/com/gp/KuryeNet/KuryeNetApplication.java`
- **Katmanlar**
  - API (Controller): `src/main/java/com/gp/KuryeNet/API/controllers`
  - Business/Service: `src/main/java/com/gp/KuryeNet/business` ve `src/main/java/com/gp/KuryeNet/core/business`
  - DataAccess (Repository): `src/main/java/com/gp/KuryeNet/dataAccess` ve `src/main/java/com/gp/KuryeNet/core/dataAccess`
  - Entities/DTO: `src/main/java/com/gp/KuryeNet/entities` ve `src/main/java/com/gp/KuryeNet/core/entities`
  - Utils/Exception/JWT: `src/main/java/com/gp/KuryeNet/core/utulities`

## 3) Konfigürasyon ve Çalışma Ortamı
- **DB**: PostgreSQL
- **JPA**: `spring.jpa.hibernate.ddl-auto=none` (şema dışarıdan yönetiliyor)
- **Port**: `server.port=${PORT:8585}`
- **CORS**: Hem `WebConfig` hem `SimpleCorsFilter` var. (Detaylar Güvenlik bölümünde)

Dosya: `src/main/resources/application.properties`

## 4) Veri Modeli (ER)
### 4.1 Varlıklar
- Address, Courier, Customer, CustomerBasket, Order, Provider, Vehicle
- User, Role, UserRole

### 4.2 İlişkiler (özet)
- Address 1—* Order/Courier/Provider/Customer
- Vehicle 1—* Courier
- Courier 1—* Order
- Provider 1—* Order
- Customer 1—* Order
- Order 1—* CustomerBasket
- User *—* Role (UserRole üzerinden)

### 4.3 Şema Notları
- Order, Courier ve Customer gibi temel tablolar; adres/araç ilişkilerini `@ManyToOne` üzerinden kuruyor.
- `CustomerBasket` ara tablosu, Order ve Customer arasındaki ilişkiyi taşıyor.
- User/Role modeli ayrı bir kimlik alanı olarak tutuluyor.

## 5) İş Katmanı ve Akışlar
### 5.1 Sipariş Akışları
- `OrderManager`: CRUD, filtre, sıralama, sayfalama, DTO projeleme.
- `OrderCheckService` doğrulama/iş kuralları ile `Utils.getErrorsIfExist` üzerinden hata toplama.

### 5.2 Kurye Akışları
- **Start Order**: kurye ve sipariş statü güncelleniyor, AI tahmin çağrısı tetikleniyor.
- **End Order**: teslimat zamanı hesaplanıyor, günlük/toplam gönderim sayaçları güncelleniyor, AI write_data gönderiliyor.

### 5.3 Auth ve Kullanıcı Yönetimi
- `AuthManager`: Courier/Customer rolleriyle kullanıcı oluşturma.
- JWT ile sessionless auth.

### 5.4 AI Model Entegrasyonu
- AI model için feature set hazırlanıyor (şehir sınıfı, hava, trafik yoğunluğu, mesafe vb.)
- Dış ML servisine `predict` ve `write_data` çağrıları yapılıyor.

## 6) API Uçları (Özet)
Başlıca uçlar:
- `/authentication/login`
- `/auth/registerCourier`, `/auth/registerCustomer`
- `/api/orders/*`
- `/api/couriers/*`
- `/api/customers/*`
- `/api/addresses/*`
- `/api/providers/*`
- `/api/vehicles/*`
- `/api/customersbaskets/*`
- `/api/aimodel/*`
- `/api/googlemaps/*`
- `/api/openweathermap/*`
- `/api/users/*`, `/api/roles/*`, `/api/usersroles/*`

Detaylı path listesi `docs/openapi.yaml` içinde.

## 7) Güvenlik
- JWT ile Bearer auth.
- Kural setleri `WebSecurityConfiguration` içinde rol bazlı.
- `SimpleCorsFilter` origin’i yansıtıyor (fiilen çok gevşek). `WebConfig` ise dar bir origin listesi kullanıyor. Bu ikisi çakışıyor.
- JWT secret ve API key’ler kod içine gömülü (güvenlik riski).

## 8) Dış Servisler
- Google Maps Directions API
- OpenWeatherMap OneCall 3.0
- ML Flask API (Heroku)

## 9) Testler
- 3 test sınıfı var. Bazı testler gerçek endpoint ve response alanlarıyla uyumsuz.
- Mevcut testler, gerçek entegrasyon yerine mocklama stratejisine ihtiyaç duyuyor.

## 10) Riskler ve Öneriler
1) **Secret/Key’leri env üzerinden yönet**
2) **CORS tek bir yerden yönet**
3) **AIModelManager’da String karşılaştırmalarını düzelt**
4) **Asenkron/reaktif + bloklayıcı RestTemplate kullanımını sadeleştir**
5) **Testleri endpoint/response gerçekliğine göre güncelle**

## 11) Ekler
- ER diyagramı: `docs/er-diagram.mmd`
- OpenAPI dokümanı: `docs/openapi.yaml`
