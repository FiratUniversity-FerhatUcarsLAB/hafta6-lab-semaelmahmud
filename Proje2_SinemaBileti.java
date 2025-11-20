import java.util.Scanner;
import java.text.DecimalFormat;

public class sinemabileti {

    // 1) Hafta sonu mu?
    public static boolean isWeekend(int gun) {
        return gun == 6 || gun == 7;
    }

    // 2) Matine mi? (12:00 öncesi)
    public static boolean isMatinee(int saat) {
        return saat < 12;
    }

    // 3) Temel fiyat hesapla
    public static double calculateBasePrice(int gun, int saat) {
        boolean weekend = isWeekend(gun);
        boolean matinee = isMatinee(saat);

        if (!weekend) { // Hafta içi
            return matinee ? 45.0 : 65.0;
        } else { // Hafta sonu
            return matinee ? 55.0 : 85.0;
        }
    }

    // 4) İndirim oranı hesapla (0.0 - 1.0)
    public static double calculateDiscount(int yas, int meslek, int gun) {
        // Yaş indirimleri her zaman öncelikli
        if (yas >= 65) return 0.30;
        if (yas < 12) return 0.25;

        double indirim = 0.0;

        switch (meslek) {
            case 1: // Öğrenci
                if (gun >= 1 && gun <= 4) indirim = 0.20;
                else indirim = 0.15;
                break;

            case 2: // Öğretmen
                if (gun == 3) indirim = 0.35;
                break;

            case 3:
            default:
                indirim = 0.0;
                break;
        }
        return indirim;
    }

    // 5) Film formatı ekstra ücreti
    public static double getFormatExtra(int filmTuru) {
        switch (filmTuru) {
            case 1: return 0.0;   // 2D
            case 2: return 25.0;  // 3D
            case 3: return 35.0;  // IMAX
            case 4: return 50.0;  // 4DX
            default: return 0.0;
        }
    }

    // 6) Final fiyat hesaplama
    public static double calculateFinalPrice(int gun, int saat, int yas, int meslek, int filmTuru) {
        double base = calculateBasePrice(gun, saat);
        double indirimOrani = calculateDiscount(yas, meslek, gun);
        double indirimliBase = base * (1.0 - indirimOrani);
        double ekstra = getFormatExtra(filmTuru);
        return indirimliBase + ekstra;
    }

    // 7) Bilet bilgisi
    public static String generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru) {
        DecimalFormat df = new DecimalFormat("0.00");

        double base = calculateBasePrice(gun, saat);
        double indirimOrani = calculateDiscount(yas, meslek, gun);
        double indirimTutar = base * indirimOrani;
        double ekstra = getFormatExtra(filmTuru);
        double toplam = calculateFinalPrice(gun, saat, yas, meslek, filmTuru);

        // Meslek string
        String meslekStr = "";
        switch (meslek) {
            case 1: meslekStr = "Öğrenci"; break;
            case 2: meslekStr = "Öğretmen"; break;
            default: meslekStr = "Diğer"; break;
        }

        // Film formatı string
        String filmStr = "";
        switch (filmTuru) {
            case 1: filmStr = "2D"; break;
            case 2: filmStr = "3D"; break;
            case 3: filmStr = "IMAX"; break;
            case 4: filmStr = "4DX"; break;
            default: filmStr = "Bilinmiyor"; break;
        }

        return  "----- BİLET BİLGİSİ -----\n" +
                "Gün: " + gun + "  Saat: " + saat + "\n" +
                "Yaş: " + yas + "  Meslek: " + meslekStr + "\n" +
                "Film Türü: " + filmStr + "\n\n" +
                "Temel Fiyat: " + df.format(base) + " TL\n" +
                "İndirim Oranı: %" + (int)(indirimOrani * 100) + "\n" +
                "İndirim Tutarı: " + df.format(indirimTutar) + " TL\n" +
                "İndirimli Temel: " + df.format(base - indirimTutar) + " TL\n" +
                "Format Ekstra: " + df.format(ekstra) + " TL\n" +
                "--------------------------\n" +
                "TOPLAM: " + df.format(toplam) + " TL\n";
    }

    // Giriş doğrulama
    private static boolean validInputs(int gun, int saat, int yas, int meslek, int filmTuru) {
        if (gun < 1 || gun > 7) return false;
        if (saat < 8 || saat > 23) return false;
        if (yas < 0 || yas > 120) return false;
        if (meslek < 1 || meslek > 3) return false;
        if (filmTuru < 1 || filmTuru > 4) return false;
        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Gün (1-7, 1=Pzt ... 7=Paz): ");
        int gun = sc.nextInt();

        System.out.print("Saat (8-23): ");
        int saat = sc.nextInt();

        System.out.print("Yaş: ");
        int yas = sc.nextInt();

        System.out.print("Meslek (1=Öğrenci, 2=Öğretmen, 3=Diğer): ");
        int meslek = sc.nextInt();

        System.out.print("Film Türü (1=2D, 2=3D, 3=IMAX, 4=4DX): ");
        int filmTuru = sc.nextInt();

        if (!validInputs(gun, saat, yas, meslek, filmTuru)) {
            System.out.println("Hatalı giriş! Lütfen değerleri kontrol edin (gün 1-7, saat 8-23, meslek 1-3, film türü 1-4).");
            return;
        }

        System.out.println();
        System.out.println(generateTicketInfo(gun, saat, yas, meslek, filmTuru));
    }
}
