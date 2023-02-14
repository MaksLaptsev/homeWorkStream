package by.laptev;

import by.laptev.model.Animal;
import by.laptev.model.Car;
import by.laptev.model.Flower;
import by.laptev.model.House;
import by.laptev.model.Person;
import by.laptev.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("------------\nTask №1\n");
        task1();
        System.out.println("------------\nTask №2\n");
        task2();
        System.out.println("------------\nTask №3\n");
        task3();
        System.out.println("------------\nTask №4\n");
        task4();
        System.out.println("------------\nTask №5\n");
        task5();
        System.out.println("------------\nTask №6\n");
        task6();
        System.out.println("------------\nTask №7\n");
        task7();
        System.out.println("------------\nTask №8\n");
        task8();
        System.out.println("------------\nTask №9\n");
        task9();
        System.out.println("------------\nTask №10\n");
        task10();
        System.out.println("------------\nTask №11\n");
        task11();
        System.out.println("------------\nTask №12\n");
        task12();
        System.out.println("------------\nTask №13\n");
        task13();
        System.out.println("------------\nTask №14\n");
        task14();
        System.out.println("------------\nTask №15\n");
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(x-> x.getAge() >=10 & x.getAge() <20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(x -> "Japanese".equals(x.getOrigin()))
                .map(x ->{
                    x.setBread(x.getBread().toUpperCase());
                    return x;
                })
                .filter(x -> "Female".equals(x.getGender()))
                .map(Animal::getBread)
                .forEach(System.out::println);

    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(x->x.getAge()>30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        long count = animals.stream()
                .filter(x -> "Female".equals(x.getGender()))
                .count();
        System.out.println(count);


    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean anyHave = animals.stream()
                .filter(x -> x.getAge() >= 20 && x.getAge() <= 30)
                .anyMatch(x->"Hungarian".equals(x.getOrigin()));
        System.out.println(anyHave);

    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean allAnimalOnlyMaleAndFemale = animals.stream()
                .allMatch(x-> "Female".equals(x.getGender()) || "Male".equals(x.getGender()));
        System.out.println(allAnimalOnlyMaleAndFemale);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean noOneFromOceania = animals.stream()
                .anyMatch(x->!"Oceania".equals(x.getOrigin()));
        System.out.println(noOneFromOceania);

    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int maxAgeFirst100 = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max().orElse(0);
        System.out.println(maxAgeFirst100);
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int mostShort = animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .mapToInt(x->x.length)
                .min().orElse(0);
        System.out.println(mostShort);
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int sumAgeAllAnimals = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println(sumAgeAllAnimals);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        double averageAgeAnimalsFromIndonesia = animals.stream()
                .filter(x -> "Indonesian".equals(x.getOrigin()))
                .mapToInt(Animal::getAge)
                .average().orElse(0);
        System.out.println(averageAgeAnimalsFromIndonesia);
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        LocalDate currentDate = LocalDate.now();
        people.stream()
                .filter(x -> Period.between(x.getDateOfBirth(),currentDate).getYears() >= 18 &&
                        Period.between(x.getDateOfBirth(),currentDate).getYears() < 27)
                .filter(x -> x.getGender().equals("Male"))
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        LocalDate currentDate = LocalDate.now();

        Stream.concat(
                Stream.concat(
                        houses.stream()
                                .filter(x -> "Hospital".equals(x.getBuildingType()))
                                .flatMap(x -> x.getPersonList().stream()),
                        houses.stream()
                                .filter(x -> !"Hospital".equals(x.getBuildingType()))
                                .flatMap(x -> x.getPersonList().stream())
                                .filter(x -> (Period.between(x.getDateOfBirth(),currentDate).getYears()<18)
                                        || (Period.between(x.getDateOfBirth(),currentDate).getYears()>=58) && "Female".equals(x.getGender())
                                        || (Period.between(x.getDateOfBirth(),currentDate).getYears()>=63) && "Male".equals(x.getGender()))
                ),
                houses.stream()
                        .filter(x -> !"Hospital".equals(x.getBuildingType()))
                        .flatMap(x -> x.getPersonList().stream())
                        .filter(x -> (Period.between(x.getDateOfBirth(),currentDate).getYears()>18)
                                || (Period.between(x.getDateOfBirth(),currentDate).getYears()<58) && "Female".equals(x.getGender())
                                || (Period.between(x.getDateOfBirth(),currentDate).getYears()<63) && "Male".equals(x.getGender()))
                ).limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        List<Car> turkmenistanCars = new ArrayList<>();
        List<Car> uzbekistanCars = new ArrayList<>();
        List<Car> kazakhstanCars = new ArrayList<>();
        List<Car> kyrgyzstanCars = new ArrayList<>();
        List<Car> russiaCars = new ArrayList<>();
        List<Car> mongoliaCars = new ArrayList<>();

        Predicate<Car> predicateTurkmen = x -> "Jaguar".equals(x.getCarMake()) || "White".equals(x.getColor());
        Predicate<Car> predicateUzbek = x -> x.getMass()<1500 || "BMW".equals(x.getCarMake())
                || "Lexus".equals(x.getCarMake())
                || "Chrysler".equals(x.getCarMake()) || "Toyota".equals(x.getCarMake());
        Predicate<Car> predicateKazakh = x -> x.getMass() > 4000 && "Black".equals(x.getColor())
                || "GMC".equals(x.getCarMake())
                || "Dodge".equals(x.getCarMake());
        Predicate<Car> predicateKyrgyzstan = x -> x.getReleaseYear()<1982 || "Civic".equals(x.getCarMake())
                || "Cherokee".equals(x.getCarMake());
        Predicate<Car> predicateRussia = x -> !"Yellow".equals(x.getColor()) && !"Red".equals(x.getColor())
                && !"Green".equals(x.getColor()) && !"Blue".equals(x.getColor()) || x.getPrice() > 40000;
        Predicate<Car> predicateMongolia = x -> x.getVin().contains("59");


                List<Car> carList = cars.stream()
                        .peek(x -> {if(predicateTurkmen.test(x)) turkmenistanCars.add(x);})
                        .filter(x -> !predicateTurkmen.test(x))
                        .peek(x -> {if(predicateUzbek.test(x)) uzbekistanCars.add(x);})
                        .filter(x -> !predicateUzbek.test(x))
                        .peek(x -> {if(predicateKazakh.test(x)) kazakhstanCars.add(x);})
                        .filter(x -> !predicateKazakh.test(x))
                        .peek(x -> {if(predicateKyrgyzstan.test(x)) kyrgyzstanCars.add(x);})
                        .filter(x -> !predicateKyrgyzstan.test(x))
                        .peek(x -> {if(predicateRussia.test(x)) russiaCars.add(x);})
                        .filter(x -> !predicateRussia.test(x))
                        .peek(x -> {if(predicateMongolia.test(x)) mongoliaCars.add(x);})
                        .filter(x -> !predicateMongolia.test(x))
                        .toList();

        summaryInfo("Туркменистан",turkmenistanCars);
        summaryInfo("Узбекистан",uzbekistanCars);
        summaryInfo("Казахстан",kazakhstanCars);
        summaryInfo("Кыргызстан",kyrgyzstanCars);
        summaryInfo("Россия",russiaCars);
        summaryInfo("Монголия",mongoliaCars);
        summaryPriceLogistic(turkmenistanCars,uzbekistanCars,kazakhstanCars,kyrgyzstanCars,russiaCars,mongoliaCars);
        System.out.println("Осталось невостребовано "+carList.size()+" автомобилей");



    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        String[] rangeOfLetters = new String[]{"S","R","Q","P","O","N","M","L","K","J","I","H","G","F","E","D","C"};
        Predicate<Flower> flowerPredicate = f -> {
            for (String s:rangeOfLetters) {
               if(f.getCommonName().toLowerCase().startsWith(s.toLowerCase())){
                   return true;
               }
            }
            return false;
        };

        AtomicReference<Double> summaryWaterConsumptionPerDay = new AtomicReference<>(0.0);
        int summaryPriceFlowers = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed().thenComparing(Flower::getPrice).reversed()
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flowerPredicate)
                .filter(Flower::isShadePreferred)
                .filter(x-> x.getFlowerVaseMaterial().contains("Glass") || x.getFlowerVaseMaterial().contains("Aluminum")
                || x.getFlowerVaseMaterial().contains("Steel"))
                .peek(x-> summaryWaterConsumptionPerDay.updateAndGet(v -> v + x.getWaterConsumptionPerDay()))
                .mapToInt(Flower::getPrice)
                .sum();

        summaryInfoFlowers(summaryWaterConsumptionPerDay.get(),summaryPriceFlowers, 5, 1.39);

    }

    private static void summaryInfo(String country, List<Car> carList){
        AtomicInteger mass = new AtomicInteger();
        carList.forEach(x -> mass.addAndGet(x.getMass()));
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(String.valueOf(mass))/1000*7.14).setScale(3, RoundingMode.CEILING);
        System.out.println("Для страны "+country+" суммарная масса всех автомобилей составляет - "+mass+" кг");
        System.out.println("Сумма, потраченная на транспортировку, составляет - "+price+" $");
        System.out.println("-----------------");
    }

    @SafeVarargs
    private static void summaryPriceLogistic(List<Car>... cars){
        AtomicInteger totalMassCars = new AtomicInteger();
        List<List<Car>> carList = Arrays.stream(cars)
                .peek(x -> x.forEach(car -> totalMassCars.addAndGet(car.getMass())))
                .toList();
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(String.valueOf(totalMassCars))/1000*7.14).setScale(3, RoundingMode.CEILING);
        System.out.println("Общая выручка логистической компании составляет - "+price+" $");
    }

    private static void summaryInfoFlowers(Double waterPerDayLitr, int priceFlower, int years, double priceOneKubWater){
        BigDecimal totalWater = BigDecimal.valueOf(waterPerDayLitr/1000*365*years).setScale(3,RoundingMode.CEILING) ;
        BigDecimal totalPrice = totalWater.multiply(BigDecimal.valueOf(priceOneKubWater)).setScale(3,RoundingMode.CEILING) ;
        System.out.println("Для закупки растений потребуется - "+priceFlower+" $");
        System.out.println("Общее потребление воды за "+years+" лет: "+totalWater+" кубометров");
        System.out.println("Стоимость обслуживания(полива) за "+years+" лет составит: "+totalPrice+" $"+" при цене одного кубометра в "+priceOneKubWater+" $");
    }
}