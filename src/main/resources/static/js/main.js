let counter = 0;

// Функция для добавления новой строки в таблицу
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

$(document).ready(function() {
    $("#SendBtn").click(function() {
        var form = document.getElementById('myForm');
        var formData = new FormData(form);
        var products = [];
        for (let i = 0; i <counter; i++) {
            var idAssortment = document.getElementById("product" + i).value;
            var CountProductFromAssortment = document.getElementById("CountProductFromAssortment" +i).value;
            var costForOneProduct = document.getElementById("costForOneProduct" + i).value;
            console.log(idAssortment);
            console.log(CountProductFromAssortment);
            console.log(costForOneProduct);
            var product = {
                idAssortment: idAssortment,
                CountProductFromAssortment: CountProductFromAssortment,
                costForOneProduct: costForOneProduct
            }
            console.log(product);
            products.push(product);
        }
        var data= {
            client: document.getElementById("client").value,
            orderStatus: document.getElementById("status").value,
            orderDate: document.getElementById("date").value,
            orderProductDTOS: products

        }
        console.log(data);

        $.ajax({
            url: "/admin/newOrder", // Замените на URL вашего серверного обработчика запроса
            type: "POST", // Или "GET", в зависимости от типа запроса
            data: data,
            contentType: "application/json",
            success: function(response) {
                console.log(response);
            },
            error: function(xhr, status, error) {
                console.log(error);
            }
        });
    });
});
