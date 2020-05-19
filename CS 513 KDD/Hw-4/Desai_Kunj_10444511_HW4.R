################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Apply naive bayes to the “breast cancer dataset” 
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 03/12/2020
################################################################################

## remove all objects
rm(list = ls())

#install.packages('e1071', dependencies = TRUE)
library(e1071)
library(class)

data<- read.csv(file = "/Users/kunj/Desktop/Stevens/Spring '20/CS 513/Hw-4/breast-cancer-wisconsin.data.csv",
                header = TRUE, colClasses = c('numeric',rep(x = 'factor', times = 10))
)

is.na(data) <- data == '?'
completeData <- data[complete.cases(data),]
View(completeData)
# Data loading and cleaning complete.
# Setting the Seed=1 for consistent generation
set.seed(1) 

# Now Selecting 70% of data as sample from total 'n' rows of the data  
trainRows <- sample(nrow(completeData), size = floor(.70*nrow(completeData)), replace = F)

train <- completeData[trainRows,-1]
test <- completeData[-trainRows,-1]


## Creating a naive bayes model with F1:F9 variables
nBayes_all <- naiveBayes(Class ~., data =train)

## Predicting the outputs on test data
model_test <- predict(nBayes_all,test)

## Comparing the model output with actual data
data_class<-ftable(TestData=test$Class,PredictedData = model_test)

prop.table(data_class)

## Finding all the values perdicted incorrectly in test data
NB_wrong<-sum(model_test!=test$Class)
NB_error_rate<-NB_wrong/length(model_test)
NB_accurate<-(1-NB_error_rate)*100
NB_accurate

