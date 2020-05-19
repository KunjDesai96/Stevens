###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : 6.1 Use the C5.0 methodology to develop a classification model for the Diagnosis.
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 04/25/2020
##########################################################################################

## remove all objects
rm(list=ls()) 

data <- read.csv(file.choose())
head(data)
table(data$Class) #To view number of data for Class=2 and Class=4 
data <- na.omit(data)
View(data)

data$Class <- factor(data$Class, levels = c(2,4), labels = c("Benign", "Malignant")) 

installed.packages()
?install.packages()

#install.packages("C50", repos="http://R-Forge.R-project.org")
library('C50')

set.seed(111)

idx<-sort(sample(nrow(data),round(.30*nrow(data))))

trainingData<-data[-idx,]
testData<-data[idx,]

C50_model <- C5.0( Class~.,data=trainingData )

summary(C50_model )

plot(C50_model)

C50_predict<-predict( C50_model ,testData , type="class" )
table(actual=testData[,11],C50=C50_predict)
str(C50_predict)

wrong<- (testData[,11]!=C50_predict)

error_rate<-sum(wrong)/length(testData[,11])
error_rate

