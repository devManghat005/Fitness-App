package com.example.myapplication;
    public class Food {
        private String name;

        private int id;
        private int carbohydrate;
        private int protein;
        private int fiber;
        private int fat;
        private int vitamins;
        private int quantity;
        private int calorieCount;

        public Food() {
        }


        public Food(String name, int carbohydrate, int protein, int fiber, int fat, int vitamins, int quantity, int calorie) {
            this.name = name;
            this.carbohydrate = carbohydrate;
            this.protein = protein;
            this.fiber = fiber;
            this.fat = fat;
            this.vitamins = vitamins;
            this.quantity = quantity;
            this.calorieCount = calorie;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCarbohydrate() {
            return carbohydrate;
        }

        public void setCarbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public int getProtein() {
            return protein;
        }

        public void setProtein(int protein) {
            this.protein = protein;
        }

        public int getFiber() {
            return fiber;
        }

        public void setFiber(int fiber) {
            this.fiber = fiber;
        }

        public int getFat() {
            return fat;
        }

        public void setFat(int fat) {
            this.fat = fat;
        }

        public int getVitamins() {
            return vitamins;
        }

        public void setVitamins(int vitamins) {
            this.vitamins = vitamins;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getCalorieCount() {
            return calorieCount;
        }

        public void setCalorieCount(int calorie) {
            this.calorieCount = calorie;
        }
    }
