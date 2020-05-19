rm(list=ls())
#  Firstname  :  Nihir
#  Lastname   : Patel 
#  ID         : 10444920



library(rpart)
library(rpart.plot)  		
library(rattle)          
library(RColorBrewer) 
file1<-read.csv("/Users/kunj/Desktop/Stevens/Spring '20/CS 513/Hw-4/breast-cancer-wisconsin.data.csv",na.strings = '?')
View(file1)
table(file1$Class)

file1$Class <- factor(file1$Class, levels = c(2,4),labels = c("Benign", "Malignant"))

idx<-sort(sample(nrow(file1),as.integer(.70*nrow(file1))))
training<-file1[idx,]
test<-file1[-idx,]


model<-rpart(Class~.,training[,-1])
rpart.plot(model)
prediction<-predict(model,test[,-1],type="class") 
table(test[,11],prediction)
str(prediction)
wrong<-sum(test[,11]!=prediction)
error_rate<-wrong/length(test[,11])
error_rate


library(rpart.plot)
prp(model)

fancyRpartPlot(model)

