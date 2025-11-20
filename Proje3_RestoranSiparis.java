import java.util.Scanner;

public class RestoranSiparis {

    // 1) Ana yemek fiyatı
    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85;
            case 2: return 120;
            case 3: return 110;
            case 4: return 65;
            default: return 0;
        }
    }

    // 2) Başlangıç fiyatı
    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25;
            case 2: return 45;
            case 3: return 55;
            default: return 0;
        }
    }

    // 3) İçecek fiyatı
    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15;
            case 2: return 12;
            case 3: return 35;
            case 4: return 25;
            default: return 0;
        }
    }

    // 4) Tatlı fiyatı
    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65;
            case 2: return 55;
            case 3: return 35;
            default: return 0;
        }
    }

    // 5) Combo kontrolü
    public static boolean isComboOrder(boolean ana, boolean icecek, boolean tatli) {
        return ana && icecek && tatli;
    }

    // 6) Happy hour kontrolü
    public static boolean isHappyHour(int saat) {
        return saat >= 14 && saat <= 17;
    }

    // 7) İndirimleri hesapla
    public static double calculateDiscount(double toplam, boolean combo, boolean ogrenci, int saat) {
        double indirim = 0;

        // Combo indirimi %15
        if (combo)
            indirim += toplam * 0.15;

        // Happy hour – içecek %20 zaten içecek fiyatı üzerinden ana hesapta yapılacak
        // Burada ekstra indirim yok — happy hour indirimini çağıran ana program içeceğe uygular

        // 200 TL üzeri %10
        if (toplam >= 200)
            indirim += toplam * 0.10;

        // Öğrenci indirimi (hafta içi %10) - örnekte direkt %10 uygulanmış
        if (ogrenci)
            indirim += toplam * 0.10;

        return indirim;
    }

    // 8) Bahşiş önerisi
    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }


    // *******************************
    //           MAIN METODU
    // *******************************
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Ana Yemek (1-4): ");
        int ana = sc.nextInt();

        System.out.print("Başlangıç (0-3): ");
        int bas = sc.nextInt();

        System.out.print("İçecek (0-4): ");
        int icecek = sc.nextInt();

        System.out.print("Tatlı (0-3): ");
        int tatli = sc.nextInt();

        System.out.print("Saat: ");
        int saat = sc.nextInt();

        System.out.print("Öğrenci misiniz? (E/H): ");
        boolean ogrenci = sc.next().trim().equalsIgnoreCase("E");

        System.out.print("Gün (1-7): ");
        int gun = sc.nextInt();

        // Fiyatlar
        double fAna = getMainDishPrice(ana);
        double fBas = getAppetizerPrice(bas);
        double fIcecek = getDrinkPrice(icecek);
        double fTatli = getDessertPrice(tatli);

        // Happy hour indirimi (içeceğe %20)
        if (isHappyHour(saat) && icecek != 0) {
            fIcecek = fIcecek * 0.80;
        }

        // Ara toplam
        double araToplam = fAna + fBas + fIcecek + fTatli;

        // Combo kontrolü
        boolean combo = isComboOrder(ana != 0, icecek != 0, tatli != 0);

        // İndirim hesapla
        double toplamIndirim = calculateDiscount(araToplam, combo, ogrenci, saat);

        // Ödenecek toplam
        double toplam = araToplam - toplamIndirim;

        // Bahşiş
        double bahsis = calculateServiceTip(toplam);

        // ************ ÇIKTI ************
        System.out.println("------------------------------");
        System.out.println("Ara Toplam: " + araToplam + " TL");
        System.out.println("Toplam İndirim: -" + toplamIndirim + " TL");
        System.out.println("Genel Toplam: " + toplam + " TL");
        System.out.println("Bahşiş Önerisi (%10): " + bahsis + " TL");
    }
}
