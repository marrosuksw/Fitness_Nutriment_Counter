package com.spring.fitness_application.personal_data;

public enum PhysicalActivity {
    LOW(1.4),
    LIGHT(1.6),
    MODERATE(1.75),
    HIGH(1.9),
    EXTREME(2.05);

    private final double pal;

    PhysicalActivity(double pal) {
        this.pal = pal;
    }

    public double getPal() {
        return pal;
    }
}
