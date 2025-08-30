package com.spring.fitness_application.personal_data;


public class NutrimentInterpreter {

    public NutrimentInterpreter() {}

    public double interpretProteinIntake(PersonalData personalData) {
        double proteinIntake = 0.0;
        switch (personalData.getPhysicalActivity()) {
            case LOW -> proteinIntake = 1.0;
            case LIGHT -> proteinIntake = 1.3;
            case MODERATE -> proteinIntake = 1.6;
            case HIGH -> proteinIntake = 1.8;
            case EXTREME -> proteinIntake = 2.0;
        }
        return proteinIntake * personalData.getWeight().doubleValue();
    }
    public double interpretFatIntake(PersonalData personalData) {
        double fatIntake = 0.0;
        switch (personalData.getPhysicalActivity()) {
            case LOW -> fatIntake = 0.8;
            case LIGHT -> fatIntake = 0.9;
            case MODERATE -> fatIntake = 1.0;
            case HIGH -> fatIntake = 1.3;
            case EXTREME -> fatIntake = 1.6;
        }
        return fatIntake * personalData.getWeight().doubleValue();
    }
    public double interpretCarbohydrateIntake(PersonalData personalData) {
        double carbohydrateIntake = 0.0;
        switch (personalData.getPhysicalActivity()) {
            case LOW -> carbohydrateIntake = 3.5;
            case LIGHT -> carbohydrateIntake = 4.5;
            case MODERATE -> carbohydrateIntake = 5.5;
            case HIGH -> carbohydrateIntake = 6.5;
            case EXTREME -> carbohydrateIntake = 8.0;
        }
        return carbohydrateIntake * personalData.getWeight().doubleValue();
    }
    public double calculateCalorieIntake(PersonalData personalData) {
        double totalDailyEnergyExpenditure = 0.0;
        try {
            PhysicalActivity physicalActivityLevel = personalData.getPhysicalActivity();
            if (personalData.getGender() == Gender.MALE) {
                totalDailyEnergyExpenditure =
                        physicalActivityLevel.getPal() * (
                                10.0 * personalData.getWeight().doubleValue()
                                        + 6.25 * personalData.getHeight().doubleValue()
                                        - 5.0 * personalData.getAge().doubleValue()
                                        + 5.0);
            } else if (personalData.getGender() == Gender.FEMALE) {
                totalDailyEnergyExpenditure =
                        physicalActivityLevel.getPal() * (
                                10.0 * personalData.getWeight().doubleValue()
                                        + 6.25 * personalData.getHeight().doubleValue()
                                        - 5.0 * personalData.getAge().doubleValue()
                                        - 161.0);
            }
        }
        catch (NullPointerException e) {
            System.out.println("Error in calculateNutrimentIntake " + e.getMessage());
        }
        return totalDailyEnergyExpenditure;
    }
}
