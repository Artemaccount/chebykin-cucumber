
enum Category {
    АВТО("Автомобили", "9"),
    ОРГТЕХНИКА("Оргтехника и расходники", "99"),
    ТЕЛЕФОНЫ("Телефоны", "84"),

    УМОЛЧ("По умолчанию", "1"),
    ДЕШЕВЛЕ("Дешевле", "2"),
    ДОРОЖЕ("Дороже", "3"),
    ДАТА("По дате", "4");


    String name;
    String number;

    Category(String name, String number) {
        this.name = name;
        this.number = number;
    }


    public String getNumber() {
        return number;
    }
}
