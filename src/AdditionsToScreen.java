import java.io.*;
import java.util.*;

public class AdditionsToScreen {
    private static int fileNumber;
    private List<Passenger> passengers;
    private List <Passenger> passengerFilter;
    private String titleCSV;

    public AdditionsToScreen() {
        this.fileNumber = 0;
        this.passengers = new ArrayList<>();
        createPassengerList();
    }

   private void windowFilter(String pClass,String gender,String passengerName,String embarked,String ticketNumber,
                             String cabin,String passengerNumMin,String passengerNumMax,String sibSp,String ticketCostMin,
                             String ticketCostMax,String parCh){
        if (!passengerNumMax.equals("")&&!passengerNumMin.equals("")){
            if (Integer.parseInt(passengerNumMax)<Integer.parseInt(passengerNumMin)){
                String temp = passengerNumMax;
                passengerNumMax = passengerNumMin;
                passengerNumMin = temp;
            }
        } if (!ticketCostMax.equals("")&&!ticketCostMin.equals("")){
           if (Integer.parseInt(ticketCostMax)<Integer.parseInt(ticketCostMin)){
               String temp = ticketCostMax;
               ticketCostMax = ticketCostMin;
               passengerNumMin = temp;
           }
       }
       for (Passenger passenger: this.passengers) {
           if ((pClass.equals("All") || passenger.identicalPClass(returnClassNumber(pClass))) && (gender.equals("All") || passenger.identicalGender(gender))) {
               if (passengerName.equals("") || passenger.isContainedInName(passengerName)) {
                   if (embarked.equals("All") || passenger.identicalEmbarked(returnEmbarked(embarked))) {
                       if (ticketNumber.equals("") || passenger.isContainedInTicket(ticketNumber)) {
                           if (cabin.equals("") || passenger.isContainedInCabin(cabin)) {
                               if (passengerNumMin.equals("") || passenger.isBiggerId(Integer.parseInt(passengerNumMin))) {
                                   if (passengerNumMax.equals("") || !passenger.isBiggerId(Integer.parseInt(passengerNumMax))) {
                                       if (sibSp.equals("") || passenger.isContainedInSibSp(sibSp)) {
                                           if (ticketCostMin.equals("") || !passenger.isBiggerFare(Float.valueOf(ticketCostMin))) {
                                               if (ticketCostMax.equals("") || passenger.isBiggerFare(Float.valueOf(ticketCostMax))) {
                                                   if (parCh.equals("") || passenger.isContainedInParCh(Integer.parseInt(parCh))) {
                                                       this.passengerFilter.add(passenger);
                                                   }
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
           }
       }
   }

    public String filter(String pClass,String gender,String embarked,String passengerName,String ticketNumber,String cabin,String passengerNumMin,String passengerNumMax,String sibSp,
                         String ticketCostMin,String ticketCostMax,String parCh){
        this.passengerFilter = new ArrayList<>();
        windowFilter( pClass, gender, passengerName,embarked,ticketNumber,
                cabin, passengerNumMin, passengerNumMax,sibSp,ticketCostMin,
                ticketCostMax, parCh);
        orderByName();
        createFilterSCV();
        String result = "Total Row: " + this.passengerFilter.size()+ " Survived: " +
                howManySurvived(this.passengerFilter) +" dead "+ ( howMuchNotSurvived(this.passengerFilter));
        return result;
    }

    private int returnClassNumber(String pClass){
        int classNum=Constants.PCLASS_THREE;
        switch (pClass){
            case Constants.FIRST_CLASS  -> classNum=Constants.PCLASS_ONE;
            case Constants.SECOND_CLASS -> classNum=Constants.PCLASS_TWO;
        }
        return classNum;
    }

    private char returnEmbarked(String embarked){
        char embarkedNum=Constants.CHERBOURG_CHAR;
        switch (embarked){
            case Constants.SOUTH_HAMPTON_NAME  -> embarkedNum=Constants.SOUTH_HAMPTON_CHAR;
            case Constants.QUEENSTOWN_NAME -> embarkedNum=Constants.QUEENSTOWN_CHAR;
        }
        return embarkedNum;
    }

    private  void createPassengerList() {
        String line;
        String splitBy=",";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader((Constants.PATH_TO_DATA_FILE)));
            this.titleCSV=bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) !=null){
                String[] dataOfPassenger = line.split(splitBy);
                this.passengers.add(createNewPassenger(dataOfPassenger));
            }
        }catch (IOException e){
            System.out.println("can't read from the file");
        }
    }

    private  Passenger createNewPassenger(String[] dataOfPassenger) {
        Integer id = null;
        if (!dataOfPassenger[0].equals("")){
            id = Integer.parseInt(dataOfPassenger[0]);
        }
        Integer survived = null;
        if (!dataOfPassenger[1].equals("")){
            survived = Integer.parseInt(dataOfPassenger[1]);
        }
        Integer pClass = null;
        if (!dataOfPassenger[2].equals("")){
            pClass = Integer.parseInt(dataOfPassenger[2]);
        }
        String name = dataOfPassenger[3] + dataOfPassenger[4];
        String gender =  dataOfPassenger[5] ;
        Float age = null;
        if (!dataOfPassenger[6].equals("")){
            age = Float.valueOf(dataOfPassenger[6]);
        }
        Integer sibSp = null;
        if (!dataOfPassenger[7].equals("")){
            sibSp = Integer.parseInt(dataOfPassenger[7]);
        }
        Integer parCh = null;
        if (!dataOfPassenger[8].equals("")){
            parCh = Integer.parseInt(dataOfPassenger[8]);
        }
        String ticket = dataOfPassenger[9];
        Double fare = null;
        if (!dataOfPassenger[10].equals("")){
            fare = Double.valueOf(dataOfPassenger[10]);
        }
        String cabin= dataOfPassenger[11];
        Character embarked = null;
        if (dataOfPassenger.length==13&&!dataOfPassenger[12].equals("")){
            embarked = dataOfPassenger[12].charAt(0);
        }
        return new Passenger(id,survived,pClass,name,gender,age,sibSp,parCh,ticket,fare,cabin,embarked);

    }

    public void statistics(){
        String lines = "";
        lines += "Classes: \n" + classStatist();
        lines+= "\n" + "Genders: \n" + genderStatistics();
        lines+= "\n" + "Embarked: \n" + embarkedStatist();
        lines+= "\n" + "Fare: \n" + fareStatistics();
        lines+= "\n" + "Age: \n" + ageStatistics();
        lines+= "\n" + "Relatives: \n" + relativesStatistics();
        createStatisticFile(lines);
    }

    private void createStatisticFile(String lines){
        File file = new File("statistic.txt");
        try {
            boolean success =  file.createNewFile();
            if (success){
                System.out.println("File created successfully");
            }else {
                System.out.println("File already exists");
            }
        }catch (IOException e){
            System.out.println("Something happened, cannot create file.");
        }
        if (file.exists()){
            try {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(lines);
                bufferedWriter.close();
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String classStatist() {
        String classStatistLinens = "";
        for (int i=1;i<=3;i++){
            List<Passenger> classFilter = classFilter(i);
            classStatistLinens +=" "+"Class " + i + ": ";
            classStatistLinens+=Float.toString(percentageCalculation(classFilter.size(),classFilter))+"%" + "\n";
        }
        return classStatistLinens;
    }

    private Float percentageCalculation(float all, List<Passenger> statistFilterPassenger){
       float survived = howManySurvived(statistFilterPassenger);
        return (survived/all)*Constants.ONE_HUNDRED_PERCENT;
    }

    private String embarkedStatist() {
        String embarkedStatistLinens = "";
        String embarked = "SQC";
        for (int i = 0; i< embarked.length(); i++){
            List<Passenger> embarkedFilter = embarkedFilter(embarked.charAt(i));
            embarkedStatistLinens +=" "+"Embarked" + embarked.charAt(i) + ": ";
            embarkedStatistLinens+=Float.toString(percentageCalculation(embarkedFilter.size(),embarkedFilter))+"%" + "\n";
        }
        return embarkedStatistLinens;
    }

    private String genderStatistics() {
        String genderStatistLinens = "";
        for (int i=0;i<=1;i++){
            List<Passenger> genderFilter = genderFilter(Constants.GENDERS[i]);
            genderStatistLinens +=" "+Constants.GENDERS[i]+ ": ";
            genderStatistLinens+=Float.toString(percentageCalculation(genderFilter.size(),genderFilter))+"%" + "\n";
        }
        return genderStatistLinens;
    }

    private String ageStatistics(){
        String classStatistLinens = "";
        for (int i=1;i<=6;i++){
            List<Passenger> ageFilter = ageFilter(i);
            classStatistLinens +=" "+Constants.AGES[i-1]+ ": ";
            classStatistLinens+=Float.toString(percentageCalculation(ageFilter.size(),ageFilter))+"%" + "\n";
        }

        return classStatistLinens;
    }

    private String fareStatistics(){
        String classStatistLinens = "";
        for (int i=1;i<=3;i++){
            List<Passenger> fareFilter = fareFilter(i);
            classStatistLinens +=" "+Constants.PRICES[i-1]+ ": ";
            classStatistLinens+=Float.toString(percentageCalculation(fareFilter.size(),fareFilter))+"%" + "\n";
        }

        return classStatistLinens;
    }

    private List<Passenger> ageFilter(int ageRange) {
        List<Passenger> ageFilter = new ArrayList<>();
        for (int i = 0; i < this.passengers.size(); i++) {
            if (this.passengers.get(i).ageCheck()==ageRange) {
                ageFilter.add(this.passengers.get(i));
            }
        }
        return ageFilter;
    }

    private String relativesStatistics() {
        String genderStatistLinens = "";
        List<Passenger> noRelativesFilter = new ArrayList<>();
        List<Passenger> relativesFilter = new ArrayList<>();
        for (int i = 0; i < this.passengers.size(); i++) {
            if (this.passengers.get(i).sumRelatives() == Constants.NO_RELATIVES) {
                noRelativesFilter.add(this.passengers.get(i));
            } else {
                relativesFilter.add(this.passengers.get(i));
            }
        }
        genderStatistLinens += " No relative: ";
        genderStatistLinens += Float.toString(percentageCalculation(noRelativesFilter.size(),noRelativesFilter)) + "%"+"\n";
        genderStatistLinens += " At list one relative: ";
        genderStatistLinens += Float.toString(percentageCalculation(relativesFilter.size(),relativesFilter)) + "%" + "\n";

        return genderStatistLinens;

    }

    private List<Passenger> fareFilter(int fareRange) {
        List<Passenger> fareFilter = new ArrayList<>();
        for (int i = 0; i < this.passengers.size(); i++) {
            if (this.passengers.get(i).ticketPriceCheck()==fareRange) {
                fareFilter.add(this.passengers.get(i));
            }
        }
        return fareFilter;
    }
    private void orderByName(){
        for (int i=0;i<this.passengerFilter.size();i++) {
            for (int j=i+1;j<this.passengerFilter.size();j++)
                if(this.passengerFilter.get(i).getFormattedName().compareTo(this.passengerFilter.get(j).getFormattedName())>0){
                    Passenger passenger = this.passengerFilter.get(i);
                    this.passengerFilter.set(i,this.passengerFilter.get(j));
                    this.passengerFilter.set(j,passenger);
                }
        }
    }

    private int howManySurvived(List<Passenger> passengers) {
        int survived = 0;
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).survived()){
                survived++;
            }
        }
        return survived;
    }

    private int howMuchNotSurvived(List<Passenger> passengers) {
        int notSurvived = 0;
        for (int i = 0; i < passengers.size(); i++) {
            if (!passengers.get(i).survived()){
                notSurvived++;
            }
        }
        return notSurvived;
    }

    private List<Passenger> classFilter(int pClass) {
        List<Passenger> classFilter = new ArrayList<>();
        for (int i = 0; i < this.passengers.size(); i++) {
            if (this.passengers.get(i).identicalPClass(pClass)) {
                classFilter.add(this.passengers.get(i));
            }
        }
        return classFilter;
    }

    private List<Passenger> genderFilter(String gender) {
        List<Passenger> genderFilter = new ArrayList<>();
        for (int i = 0; i < this.passengers.size(); i++) {
            if (this.passengers.get(i).identicalGender(gender)) {
                genderFilter.add(this.passengers.get(i));
            }
        }
        return genderFilter;

    }

    private List<Passenger> embarkedFilter(char embarked) {
        List<Passenger> embarkedFilter = new ArrayList<>();
        for (int i = 0; i < this.passengers.size(); i++) {
            if (this.passengers.get(i).identicalEmbarked(embarked)) {
                embarkedFilter.add(this.passengers.get(i));
            }
        }
        return embarkedFilter;
    }

    private void createFilterSCV(){
        this.fileNumber++;
        String fileName = Integer.toString(this.fileNumber);
        File file = new File(fileName+".csv");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(this.titleCSV);
            for (Passenger passenger:this.passengerFilter) {
                printWriter.print(passenger.toString());
            }
            printWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }


    }




}

