################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Solution for Q5
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
################################################################################

## remove all objects
rm(list=ls())

#install.packages('e1071', dependencies = TRUE)
library(e1071)
library(class) 

# reading data 
csvfile<-file.choose()
dataFile<-read.csv(csvfile)

# Remove the missing values. 
dataFile<- na.omit(dataFile)

# Discretize the “MonthAtHospital” into “less than 6 months” and “6 or more months”

dataFile$MonthAtHospital[ dataFile$MonthAtHospital < 6 ] <- 0 # Assigning 0 for “less than 6 months” 
dataFile$MonthAtHospital[ dataFile$MonthAtHospital >= 6 ] <- 1 # Assigning 1 for “6 or more months”
dataFile$MonthAtHospital <- factor(dataFile$MonthAtHospital, levels = c(0,1),labels = c("Less than 6 months", "6 or more months"))

# Discretize the age into “less than 35”, “35 to 50” and “51 or over”
dataFile$Age[ dataFile$Age < 35 ] <- -1
dataFile$Age[ 35 <= dataFile$Age & dataFile$Age <= 50 ] <- 0
dataFile$Age[ 51 <= dataFile$Age ] <- 1
dataFile$Age <- factor(dataFile$Age, levels = c(-1,0,1),labels = c("Less than 35", "35 to 50", "51 and over"))


# Naïve Bayes model to classify infection (“infected’) based on the other variables
nBayes_all <- naiveBayes(Infected ~., data = dataFile)

# Predicting the outputs
model_predict <- predict(nBayes_all,dataFile)

# The accuracy of the model.
NB_wrong<-sum(model_predict!= dataFile$Infected)
NB_error_rate<-NB_wrong/length(model_predict)
NB_accurate<-(1-NB_error_rate)*100
NB_accurate

