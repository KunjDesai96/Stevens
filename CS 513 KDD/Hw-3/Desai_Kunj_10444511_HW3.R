################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : apply knn to the “breast cancer dataset” 
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 03/06/2020
################################################################################
## Step 0 clean up!!!


## remove all objects
rm(list=ls())

?install.packages
# check to see if you have the kknn package
installed.packages()
#install.packages("kknn")
#Use the R library("kknn") 

library(kknn)

data<- read.csv(file = "/Users/kunj/Desktop/Stevens/Spring '20/CS 513/Hw-3/breast-cancer-wisconsin.data.csv",
                header = TRUE, colClasses = c('numeric',rep(x = 'factor', times = 10))
)
is.na(data) <- data == '?'

completeData <- data[complete.cases(data),]

# Setting the Seed so that same sample can be reporduced
set.seed(1) 
View(data)
# Now Selecting 70% of data as sample from total 'n' rows of the data  
trainRows <- sample(nrow(completeData), size = floor(.70*nrow(completeData)), replace = F)

train <- completeData[trainRows,-1]
test <- completeData[-trainRows,-1]

## K=3
predict_k3 <- kknn(formula=Class~.,train,test,k=3,kernel="rectangular")
fit_3<-fitted(predict_k3)
table(test$Class,fit_3)

## K=5
predict_k5 <- kknn(formula=Class~.,train,test,k=5,kernel="rectangular")
fit_5<-fitted(predict_k5)
table(test$Class,fit_5)

## K=10
predict_k10 <- kknn(formula=Class~.,train,test,k=10,kernel="rectangular")
fit_10<-fitted(predict_k10)
table(test$Class,fit_10)
