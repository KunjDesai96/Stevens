###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : 2 Use the Random Forest methodology to develop a classification model for the Admission_cat dataset using gre, gpa and the rank variables as predictors.
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 05/13/2020
##########################################################################################

##Step O - Remove all objects
rm(list=ls()) 

#Step 0 - Choosing the data
data <- read.csv(file.choose(),colClasses = c("NULL", rep('factor',4)))
#data$GRE <- as.factor(as.numeric(data$GRE))
#data$GPA <- as.factor(as.numeric(data$GPA))
View(data)

#Step 1 - Install Packages
#install.packages('randomForest')
library('C50')
set.seed(123)

#Step 2 - Dividing data into Test and Training datasets
idx<-sort(sample(nrow(data),round(.30*nrow(data))))
trainingData<-data[-idx,]
testData<-data[idx,]

#Step 3 - Creaitng the model
C50_model <- C5.0(ADMIT~.,data=trainingData )
summary(C50_model )
plot(C50_model)

#Step 4 - Predicting values
C50_predict<-predict( C50_model ,testData , type="class" )
table(actual=testData$ADMIT,C50=C50_predict)
str(C50_predict)
wrong<- (testData$ADMIT!=C50_predict)

#Step 6 - Finding accuracy and error rate
error_rate<-sum(wrong)/length(wrong)
error_rate
accuracy = (1-error_rate) * 100
accuracy
