###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : 2. Use the Random Forest methodology to develop a classification model for the Admission_cat dataset using gre, gpa and the rank variables as predictors.
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 05/13/2020
##########################################################################################

##Step O - Remove all objects
rm(list=ls()) 

#Step 0 - Choosing the data
data <- read.csv(file.choose(),colClasses = c("NULL", rep('factor',4)))
View(data)

#Step 1 - Install Packages
#install.packages('randomForest')
library(randomForest)
set.seed(123)

#Step 2 - Cleaning the data
admission_dataSet <- na.omit(data)
admission_dataSet
summary(admission_dataSet)

#Step 3 - Dividing data into Test and Training datasets
idx<-sort(sample(nrow(admission_dataSet),round(.30*nrow(admission_dataSet))))
training<-data[-idx,]
test<-data[idx,]

#Step 4 - Creaitng the model
fit <- randomForest( ADMIT~., data=training, importance=TRUE, ntree=1000)
importance(fit)
varImpPlot(fit)

#Step 5 - Predicting values
Prediction <- predict(fit, test)
table(actual=test$ADMIT,Prediction)

#Step 6 - Finding accuracy and error rate
wrong<- (test$ADMIT!=Prediction )
error_rate<-sum(wrong)/length(wrong)
error_rate 
accuracy = (1-error_rate) * 100
accuracy


