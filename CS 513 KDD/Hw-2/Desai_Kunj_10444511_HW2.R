################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Home work 1
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 03/06/2020
################################################################################

# Clearing previous data
rm(list = ls())

# 1-Load the “breast-cancer-wisconsin.data.csv” from canvas into R and perform the EDA analysis by:
dataFile <- read.csv("/Users/kunj/Desktop/Stevens/Spring '20/CS 513/Hw-2/breast-cancer-wisconsin.data.csv")
View(dataFile)
x <- data.frame(dataFile)
for (variable in 1:ncol(x)) {
  x[,variable] <- as.numeric(x[,variable])
}

# I.Summarizing each column (e.g. min, max, mean )
summary(dataFile)

# II.Identifying missing values
missing_values_check <- is.na(dataFile)
missing_values <- dataFile[!complete.cases(dataFile),]

#III.Replacing the missing values with the “mean” of the column.
for(i in 1:ncol(x)){
  x[is.na(x[,i]), i] <- trunc(mean(x[,i], na.rm = TRUE))
}

#IV.Displaying the frequency table of “Class” vs. F6
frequency_table <- table(dataFile$F6,dataFile$Class)
print(frequency_table)

#V.	Displaying the scatter plot of F1 to F6, one pair at a time
dev.off()
pairs(x[,2:7], upper.panel = NULL)
title("Scatter Plot")

#VI.Show histogram box plot for columns F7 to F9
dev.off()
par(mfrow = c(2,3))
boxplot(x$F7, horizontal=TRUE,  outline=TRUE,ylim=c(0,10), frame=F, col = "red")
boxplot(x$F8, horizontal=TRUE,  outline=TRUE,ylim=c(0,10), frame=F, col = "red")
boxplot(x$F9, horizontal=TRUE,  outline=TRUE,ylim=c(0,10), frame=F, col = "red")
hist(x$F7,xlim=c(0,10), col = "yellow")
hist(x$F8,xlim=c(0,10), col = "yellow")
hist(x$F9,xlim=c(0,10), col = "yellow")

#2- Delete all the objects from your R- environment. Reload the “breast-cancer-wisconsin.data.csv” from canvas into R. Remove any row with a missing value in any of the columns

#Delete all the objects from your R- environment.
rm(list = ls())

#Reload the “breast-cancer-wisconsin.data.csv” from canvas into R.
data_File1 <-read.csv("/Users/kunj/Desktop/Stevens/Spring '20/CS 513/Hw-2/breast-cancer-wisconsin.data.csv")


#Remove any row with a missing value in any of the columns

data_File1<-data_File1[complete.cases(data_File1),]
View(data_File1)
