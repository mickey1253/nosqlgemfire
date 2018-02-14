package io.pivotal;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by 505007855 on 6/27/2017.
 */

@Repository
public class Java8Repository {

    public void helloworld(){

        List<String> names1 = new ArrayList<>();
        names1.add("Mahesh");
        names1.add("Suresh");
        names1.add("Ramesh");
        names1.add("Kalpesh");

        System.out.println(names1);

        System.out.println(" ----- Sort using Java7 syntax: -----");

        Collections.sort(names1, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

        System.out.println(names1);

        List<String> names2 = new ArrayList<>();
        names2.add("Mashesh");
        names2.add("Suresh");
        names2.add("Ramesh");
        names2.add("Kalpesh");

        System.out.println(names2);

        System.out.println(" ----- Sort using Java8 syntax: -----");

        Collections.sort(names2, (s1, s2) -> s1.compareTo(s2));

        System.out.println(names2);
    }

    public void lambdaTest(){

        // with type declaration
        MathOperation addition = (int a, int b) -> a + b;

        // without type declaration
        MathOperation subtraction = (a, b) -> a - b;

        // with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> {return a * b;};

        // without return statement and without curly braces
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + operate(10, 5, addition));
        System.out.println("10 - 5 = " + operate(10, 5, subtraction));
        System.out.println("10 * 5 = " + operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + operate(10, 5, division));


        // with parenthesis
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        greetService1.sayMessage("Mahesh");

        GreetingService greetingService2 = (message) ->
        System.out.println("Hello " + message);

        greetingService2.sayMessage("Suresh");
    }


  public void referenceMethod(){
        List names = new ArrayList();

        names.add("Mahesh");
        names.add("Suresh");
        names.add("Ramesh");
        names.add("Naresh");
        names.add("Kalpesh");

        names.forEach(System.out::println);

  }

  public void newFunctionTest(){
      List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);

      //  Predicate<Integer> predicate = n -> true;
      // n is passed as parameter to test method of Predicate interface
      // test method will always return true no matter what value n has.

      System.out.println("Print all numbers: ");

      // pass n as parameter
      eval(list, n->true);

      System.out.println("Print even numbers: ");
      eval(list, n-> n%2 == 0);

      System.out.println("Print numbers greater than 3: ");
      eval(list, n-> n > 3);
  }


  public void defultMethod(){
      Vehicle vehicle = new Car();
      vehicle.print();
  }


    public void streamTest(){

      // Count empty strings
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "jkl");
        System.out.println("List: " + strings);
        Long count = new Long(getCountEmptyStringUsingJava7(strings));
        System.out.println("Empty Strings: " + count);

        count = new Long(getCountLength3UsingJava7(strings));
        System.out.println("Strings of length 3: " + count);

        //Eliminate empth string
        List<String> filtered = deleteEmptyStringsUsingJava7(strings);
        System.out.println("Filtered List: " + filtered);

        //Eliminate empty string and join using comma.
        String mergedString = getMergedStringUsingJava7(strings, ", ");
        System.out.println("Merged String: " + mergedString);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        //get list of square of distinct numbers
        List<Integer> squaresList = getSquares(numbers);
        System.out.println("Squares List: " + squaresList);

        List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);

        System.out.println("List: " + integers);
        System.out.println("Highest number in List: " + getMax(integers));
        System.out.println("Lowest number in List: " + getMin(integers));
        System.out.println("Sum of all numbers: " + getSum(integers));
        System.out.println("Average of all numbers: " + getAverage(integers));
        System.out.println("Random Numbers: ");

        //print ten random numbers;
        Random random = new Random();

        for(int i = 0; i < 10; i++){
            System.out.println(random.nextInt());
        }


        System.out.println("Using Java8: ");
        System.out.println("List: " + strings);

        count = strings.stream().filter(string->string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);

        count = strings.stream().filter(string->string.length() == 3).count();
        System.out.println("Strubfs of length 3: " + count);

        filtered = strings.stream().filter(string->!string.isEmpty()).collect(Collectors.toList());
        System.out.println("Filtered List: " + filtered);

        mergedString = strings.stream().filter(string->!string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Merged String: " + mergedString);

        squaresList = numbers.stream().map(i ->i * i).distinct().collect(Collectors.toList());
        System.out.println("Squares List: " + squaresList);

        System.out.println("List: " + integers);
        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("Highest number in List: " + stats.getMax());
        System.out.println("Lowest number in List: " + stats.getMin());
        System.out.println("Sum of all numbers: " + stats.getSum());
        System.out.println("Average of all numbers: " + stats.getAverage());
        System.out.println("Random Numbers: ");

        random.ints().limit(10).sorted().forEach(System.out::println);

        // parallel processing

        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);

    }

    private int getCountEmptyStringUsingJava7(List<String> strings){

        int count = 0;

        for(String string: strings){
            if(string.isEmpty()){
                count++;
            }
        }

        return count;
    }

    private int getCountLength3UsingJava7(List<String> strings){
        int count = 0;

        for (String string : strings ) {
            if(string.length() == 3){
                count++;
            }
        }

        return count;
    }

    private List<String> deleteEmptyStringsUsingJava7(List<String> strings){
        List<String> filteredList = new ArrayList<String>();

        for (String string : strings ) {

            if(!string.isEmpty()){
                filteredList.add(string);
            }
        }

        return  filteredList;
    }

    private String getMergedStringUsingJava7(List<String> strings, String sperator){
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            if(!string.isEmpty()){
                stringBuilder.append(string);
                stringBuilder.append(sperator);
            }
        }
        String mergedString = stringBuilder.toString();
        return mergedString.substring(0, mergedString.length()-2);
    }

    private List<Integer> getSquares(List<Integer> numbers){
        List<Integer> squaresList = new ArrayList<>();

        for (Integer number : numbers) {
            Integer square = new Integer(number.intValue() * number.intValue());

            if(!squaresList.contains(square)){
                squaresList.add(square);
            }
        }
        return squaresList;
    }

    private int getMax(List<Integer> numbers){
        int max = numbers.get(0);

        for(int i = 1; i < numbers.size(); i++){
            Integer number = numbers.get(i);

            if(number.intValue() > max){
                max = number.intValue();
            }
        }

        return max;
    }


    private int getMin(List<Integer> numbers){
        int min = numbers.get(0);

        for(int i = 1; i < numbers.size(); i++){
            Integer number = numbers.get(i);

            if(number.intValue() < min){
                min = number.intValue();
            }
        }

        return min;
    }

    private int getSum(List numbers){
        int sum = (int)(numbers.get(0));

        for(int i = 1; i < numbers.size(); i++){
            sum = sum + (int)numbers.get(i);
        }

        return sum;
    }

    private int getAverage(List<Integer> numbers){
        return getSum(numbers) / numbers.size();
    }

  public static void eval(List<Integer> list, Predicate<Integer> predicate){
      for(Integer n: list){
          if(predicate.test(n)){
              System.out.println(n + " ");
          }
      }
  }


    interface  MathOperation{
        int operation(int a, int b);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    interface  GreetingService{
        void sayMessage(String message);
    }

    public void optionalTest(){

        Integer value1 = null;
        Integer value2 = new Integer(10);

        // Optional.ofNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.ofNullable(value1);

        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);

        System.out.println(sum(a, b));

    }

    public Integer sum(Optional<Integer>a, Optional<Integer> b){
        // Optional.isPresent - checks the value is present or not

        System.out.println("First parameter is present: " + a.isPresent());
        System.out.println("Second parameter is present: " + b.isPresent());

        //Optional.orElse - returns the value if present otherwise returns the default value passed.
        Integer value1 = a.orElse(new Integer((0)));

        // Optional.get - gets the value, value should be present
        Integer value2 = b.get();
        return value1 + value2;
    }

}

interface Vehicle{
    default void print(){
        System.out.println("I'm a vehicle!");
    }

    static void blowHorn(){
        System.out.println("Blowing horn!!!");
    }
}

interface FourWheeler{
    default void print(){
        System.out.println("I'm a four wheeler!");
    }
}

class Car implements Vehicle, FourWheeler{
    public void print(){
        Vehicle.super.print();
        FourWheeler.super.print();
        Vehicle.blowHorn();
        System.out.println("I am a car!");
    }
}

