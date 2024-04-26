let counter = 0;
function addRow() {
    var table = document.getElementById("myTable");
    var newRow = table.insertRow();

    var dropdownCell = newRow.insertCell();
    var dropdown = document.createElement("select");
    dropdown.id ="product" + counter;

    // Вызов AJAX-запроса для получения списка продуктов
    fetch('/admin/productsList')
        .then(response => response.json())
        .then(productList => {
            productList.forEach(x => {
                var option = document.createElement("option");
                option.value = x.idAssort;
                option.text = x.productName;
                dropdown.add(option);
            });
        })
        .catch(error => console.log(error));
    dropdownCell.appendChild(dropdown);
    var floatCell = newRow.insertCell();
    var floatInput = document.createElement("input");
    floatInput.type = "number";
    floatInput.step = "0.01";
    floatInput.classList.add("input-cell");
    floatInput.id = "CountProductFromAssortment" + counter;
    console.log("CountProductFromAssortment" + counter);
    floatCell.appendChild(floatInput);

    var numberCell = newRow.insertCell();
    var numberInput = document.createElement("input");
    numberInput.type = "number";
    numberInput.classList.add("input-cell");
    numberInput.id = "costForOneProduct" + counter;
    numberCell.appendChild(numberInput);
    counter++;
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
$(document).ready(function() {
    $("#SendBtn").click(function() {
        var form = document.getElementById('myForm');
        var formData = new FormData(form);
        var products = [];
        for (let i = 0; i <counter; i++) {
            var idAssortment = document.getElementById("product" + i).value;
            var CountProductFromAssortment = document.getElementById("countProductFromAssortment" +i).value;
            var costForOneProduct = document.getElementById("costForOneProduct" + i).value;
            console.log(idAssortment);
            console.log(CountProductFromAssortment);
            console.log(costForOneProduct);
            var product = {
                idAssortment: idAssortment,
                countProductFromAssortment: CountProductFromAssortment,
                costForOneProduct: costForOneProduct
            }
            console.log(product);
            products.push(product);
        }
        var data= {
            client: document.getElementById("client").value,
            orderStatus: document.getElementById("status").value,
            orderDate: document.getElementById("date").value
        }
        console.log(data);

        send('/admin/newOrderProduct', 'POST', products).then(r  =>{
            form.submit();

        })
    });
});