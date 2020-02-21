function isPrime(value) {
    for (var i = 2; i < value; i++) {
        if (value % i === 0) {
            return false;
        }
    }
    return true;
}

const staticForm = document.getElementById("static-form");

if (staticForm) {
    const checkNumber = document.getElementById("number");

    const errorContainer = document.getElementById("error-container");
    const errorTextElement = errorContainer.getElementsByClassName(
        "text-goes-here"
    )[0];



    staticForm.addEventListener("submit", event => {
        event.preventDefault();

        try {
            errorContainer.classList.add("hidden");
            errorTextElement.textContent = "";

            const numberValue = parseInt(checkNumber.value);

            var resultStr;
            if (isNaN(numberValue)) throw "Please provide number.";

            if (numberValue <= 1) {
                var orderList = document.getElementById("attempts");
                var listEntry = document.createElement("li");
                listEntry.className = "not-prime";
                var tempresult = numberValue + " is not a prime number."
                listEntry.appendChild(document.createTextNode(tempresult));
                orderList.appendChild(listEntry);
            } else {
                const result = isPrime(numberValue);

                if (result == true) {
                    var orderList = document.getElementById("attempts");
                    var listEntry = document.createElement("li");
                    listEntry.className = "is-prime";
                    resultStr = numberValue + " is a prime number."
                    listEntry.appendChild(document.createTextNode(resultStr));
                    orderList.appendChild(listEntry);
                } else {
                    var orderList = document.getElementById("attempts");
                    var listEntry = document.createElement("li");
                    listEntry.className = "not-prime";
                    resultStr = numberValue + " is not a prime number."
                    listEntry.appendChild(document.createTextNode(resultStr));
                    orderList.appendChild(listEntry);
                }
            }
            checkNumber.value = "";
        } catch (e) {
            const message = typeof e === "string" ? e : e.message;
            checkNumber.focus();
            errorTextElement.style.color = "#ff0000";
            errorTextElement.textContent = message;
            errorContainer.classList.remove("hidden");
        }
    });
}