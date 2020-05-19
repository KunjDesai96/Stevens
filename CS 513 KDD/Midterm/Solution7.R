################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Solution for Q7
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
################################################################################

## remove all objects
rm(list=ls())

#install.packages("kknn")
#Use the R library("kknn") 
library(kknn)

# reading data 
csvfile<-file.choose()
dataFile<-read.csv(csvfile)
View(dataFile)

# Remove the missing values. 
dataFile<-dataFile[complete.cases(dataFile),]

##########################################################
trainRows <- sort(sample(nrow(dataFile), size = floor(.70*nrow(dataFile))))
train <- dataFile[trainRows,]
test <- dataFile[-trainRows,]

## K=5
dataFile$Infected <- as.factor(dataFile$Infected)
predict_k5 <- kknn(formula=Infected~., train, test[,-7], k=5,kernel ="rectangular" )
fit <- fitted(predict_k5)
table(test$Infected,Fitted=fit)
