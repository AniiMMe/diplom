let count =0;
function addRow() {
    var table = document.getElementById("myTable");
    var newRow = table.insertRow();

    var dropdownCell = newRow.insertCell();
    var dropdown = document.createElement("select");
    dropdown.id = "idAssortment" + count;
    fetch('/admin/productsList')
        .then(response => response.json())
        .then(data => {
            data.forEach(x=>{
                var option = document.createElement("option");
                option.value = x.idAssort;
                option.text = x.productName;
                dropdown.add(option);
            })

        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
    dropdownCell.appendChild(dropdown);

    var dateCell1 = newRow.insertCell();
    var dateInput1 = document.createElement("input");
    dateInput1.id="productStartdata" + count;
    dateInput1.type = "date";
    dateInput1.classList.add("input-cell");
    dateCell1.appendChild(dateInput1);

    var dateCell2 = newRow.insertCell();
    var dateInput2 = document.createElement("input");
    dateInput2.id ="productEnddata" + count;
    dateInput2.type = "date";
    dateInput2.classList.add("input-cell");
    dateCell2.appendChild(dateInput2);

    var floatCell = newRow.insertCell();
    var floatInput = document.createElement("input");
    floatInput.id ="costForOneProduct" + count;
    floatInput.type = "number";
    floatInput.step = "0.01";
    floatInput.classList.add("input-cell");
    floatCell.appendChild(floatInput);

    var numberCell = newRow.insertCell();
    var numberInput = document.createElement("input");
    numberInput.id ="CountProductFromAssortment" + count;
    numberInput.type = "number";
    numberInput.classList.add("input-cell");
    numberCell.appendChild(numberInput);
    count++;
}
async function send(url, method, data){
    await fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            // Обработка полученных данных
            console.log(data);
        })
        .catch(error => {
            // Обработка ошибок
            console.error('Ошибка:', error);
        });
}
// Получаем ссылку на форму
var form = document.getElementById("myForm");

// Назначаем обработчик события submit
form.addEventListener("submit", function(event) {
    // Предотвращаем отправку формы по умолчанию
    event.preventDefault();
    var formData = new FormData(form);
    let flag=0;
    productsList =[];
    for (let i=0; i<count; i++){
        const idAssortment = document.getElementById("idAssortment"+i).value;
        const productStartdata = document.getElementById("productStartdata"+i).value;
        const productEnddata = document.getElementById("productEnddata"+i).value;
        const costForOneProduct = document.getElementById("costForOneProduct"+i).value;
        const CountProductFromAssortment = document.getElementById("CountProductFromAssortment"+i).value;
        if (productStartdata> Date.now() || productStartdata< new Date("2020-08-15")) {
            flag++;
            alert("Некорректная дата!");
        }
        if (productEnddata< Date.now() || productStartdata> new Date("2040-08-15")) {
            flag++;
            alert("Некорректная дата срока годности!");
        }
        if (costForOneProduct<0) {
            flag++;
            alert("Стоимость не может быть отрицательной!");
        }
        if (CountProductFromAssortment<0) {
            flag++;
            alert("Количество не может быть отрицательным!");
        }

        const product ={
            idAssortment:idAssortment,
            countProductFromAssortment:CountProductFromAssortment,
            costForOneProduct:costForOneProduct,
            productStartdata:productStartdata,
            productEnddata:productEnddata
        }
        productsList.push(product)
    }
    if (flag ===0) {
        send('/user/newSupplyProduct', 'POST', productsList).then(r => {
            form.submit();
        })
    }

})
