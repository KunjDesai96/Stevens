###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : 6.2 Use the Random Forest methodology to develop a classification model for the Diagnosis and identify important features.
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 04/25/2020
##########################################################################################


## remove all objects
rm(list=ls()) 

data <- read.csv(file.choose(),colClasses = c("NULL", rep('factor',10)))

#install.packages('randomForest')

library(randomForest)

?randomForest()
?importance()
?tuneRF()

?read.csv()
set.seed(123)
set.seed(123)
?ifelse

## Dividing data into Test and Training datasets
idx<-sort(sample(nrow(data),round(.25*nrow(data))))
training<-data[-idx,]
test<-data[idx,]

## creaitng the model
fit <- randomForest( Class~., data=training, importance=TRUE, ntree=1000)
importance(fit)
varImpPlot(fit)
Prediction <- predict(fit, test)
table(actual=test[,10],Prediction)


wrong<- (test[,10]!=Prediction )
error_rate<-sum(wrong)/length(wrong)
error_rate 