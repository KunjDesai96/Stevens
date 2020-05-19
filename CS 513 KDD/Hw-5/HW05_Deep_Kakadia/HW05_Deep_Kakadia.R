###############################
# Project: HW 05
# First Name: Deep  
#Last Name: Kakadia
#CWID: 10446430
#Company: Stevens
#Class: 513 A
#Date: 27 October 2019
############################### 

rm(list=ls()) 

cancerData <- read.csv(file.choose())
head(cancerData)
table(cancerData$Class) #To view number of data for Class=2 and Class=4 

#newCancerData <- na.omit(cancerData)
#tot <- nrow(newCancerData) #to get total rows without NA. 

cancerData$Class <- factor(cancerData$Class, levels = c(2,4), labels = c("Benign", "Malignant")) 

installed.packages()

#install.packages("rpart")  # CART standard package
?install.packages()

#install.packages("rpart")
#install.packages("rpart.plot")     # Enhanced tree plots
#install.packages("rattle")         # Fancy tree plot
#install.packages("RColorBrewer")   # colors needed for rattle

library(rpart)
library(rpart.plot)  			# Enhanced tree plots
library(rattle)           # Fancy tree plot
library(RColorBrewer)     # colors needed for rattle

set.seed(111)

index<-sort(sample(nrow(cancerData),round(.25*nrow(cancerData))))

training<-cancerData[-index,]
test<-cancerData[index,]

?rpart()
#Grow the tree 
dev.off()
CART_class<-rpart( Class~.,data=training)
rpart.plot(CART_class)
CART_predict2<-predict(CART_class,test, type="class") 
table(Actual=test[,11],CART=CART_predict2)
CART_predict<-predict(CART_class,test) 


CART_predict<-predict(CART_class,test)
str(CART_predict)
CART_predict_cat<-ifelse(CART_predict[,1]<=.5,'Malignant','Benign')


table(Actual=test[,11],CART=CART_predict_cat)
CART_wrong<-sum(test[,11]!=CART_predict_cat)
CART_error_rate<-CART_wrong/length(test[,11])
CART_error_rate
CART_predict2<-predict(CART_class,test, type="class")
CART_wrong2<-sum(test[,11]!=CART_predict2)
CART_error_rate2<-CART_wrong2/length(test[,11])
CART_error_rate2 

library(rpart.plot)
prp(CART_class)


# much fancier graph
fancyRpartPlot(CART_class) 




