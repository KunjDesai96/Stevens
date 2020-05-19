################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Solution for Q2
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
################################################################################

## remove all objects
rm(list=ls())

# reading data 
csvfile<-file.choose()
dataFile<-read.csv(csvfile)


#I.	Summarizing each column (e.g. min, max, mean )
summary(dataFile)

#II.	Identifying missing values
missing_values_check <- is.na(dataFile)
missing_values <- dataFile[!complete.cases(dataFile),]

#III.	Displaying the frequency table of “Infected” vs. “MaritalStatus” 
frequency_table <- table(dataFile$Infected,dataFile$MaritalStatus)
print(frequency_table)

#IV.	Displaying the scatter plot of “Age”, “MaritalStatus” and “MonthAtHospital”, one pair at a time
dev.off()
pairs(dataFile[, c("Age", "MaritalStatus", "MonthAtHospital")])
title("Scatter Plot")

#V.	Show box plots for columns:  “Age”, “MaritalStatus” and “MonthAtHospital”
boxplot(dataFile[, c("Age", "MaritalStatus", "MonthAtHospital")])
title("Box Plot")

#VI.	Replacing the missing values of “Cases” with the “mean” of “Cases”.
dataFile[is.na(dataFile[,c("Cases")])] <- mean(dataFile[,c("Cases")], na.rm = TRUE)