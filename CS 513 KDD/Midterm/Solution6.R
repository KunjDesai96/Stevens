################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Solution for Q6
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
################################################################################

## remove all objects
rm(list=ls())

#install.packages("rpart")  # CART standard package
#install.packages("rpart.plot")     # Enhanced tree plots
#install.packages("rattle")         # Fancy tree plot
#install.packages("RColorBrewer")   # colors needed for rattle

library(rpart)
library(rpart.plot)  			# Enhanced tree plots
library(rattle)           # Fancy tree plot
library(RColorBrewer)     # colors needed for rattle

# reading data 
csvfile<-file.choose()
dataFile<-read.csv(csvfile)

# Remove the missing values. 
dataFile<-na.omit(dataFile)

# Discretize the “MonthAtHospital” into “less than 6 months” and “6 or more months”

dataFile$MonthAtHospital[ dataFile$MonthAtHospital < 6 ] <- 0 # Assigning 0 for “less than 6 months” 
dataFile$MonthAtHospital[ dataFile$MonthAtHospital >= 6 ] <- 1 # Assigning 1 for “6 or more months”
dataFile$MonthAtHospital <- factor(dataFile$MonthAtHospital, levels = c(0,1),labels = c("Less than 6 months", "6 or more months"))

# Discretize the age into “less than 35”, “35 to 50” and “51 or over”
dataFile$Age[ dataFile$Age < 35 ] <- -1
dataFile$Age[ 35 <= dataFile$Age & dataFile$Age <= 50 ] <- 0
dataFile$Age[ 51 <= dataFile$Age ] <- 1
dataFile$Age <- factor(dataFile$Age, levels = c(-1,0,1),labels = c("Less than 35", "35 to 50", "51 and over"))


# CART model to classify infection (“infected’) based on the other variables

index <- sort(sample(nrow(dataFile), as.integer(.70*nrow(dataFile))))

training <- dataFile[index,]
test <- dataFile[-index,]
dataFile$Infected <- as.factor(dataFile$Infected)

# Grow the tree 
CART_class<-rpart( Infected~.,training[,-1])
rpart.plot(CART_class, roundint = FALSE)

# Predicting the outputs
CART_predict<-predict(CART_class,test[,-1], type="class")
table(test[,7],CART_predict)

# The accuracy of the model.
CART_wrong<-sum(test[,7]!= CART_predict)
CART_error_rate<-CART_wrong/length(test[,7])
CART_accurate<-(1-CART_error_rate)*100
CART_accurate

# much fancier graph
fancyRpartPlot(CART_class)




