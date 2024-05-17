document.addEventListener("DOMContentLoaded", function () {
    fetch("/admin/getListMarkForAgency", {
        method: 'GET'
    }) // Замените URL на свой
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Ошибка запроса на сервер");
        })
        .then(function (data) {
            var xValues = [0, 1, 2, 4, 5, 6, 7, 8, 9, 10];
            new Chart("myChart", {
                type: "line",
                data: {
                    labels: xValues,
                    datasets: [{
                        data: data.markQuality,
                        borderColor: "#F8BBD0",
                        fill: false
                    }, {
                        data: data.prinProdQuality,
                        borderColor: "#E1BEE7",
                        fill: false
                    }, {
                        data: data.otpProdQuality,
                        borderColor: "#B3E5FC",
                        fill: false
                    }, {
                        data: data.qualityProduct,
                        borderColor: "#C5E1A5",
                        fill: false
                    }, {
                        data: data.comunicationQuality,
                        borderColor: "#E6EE9C",
                        fill: false
                    }]
                },
                options: {
                    legend: {display: false}
                }
            });
        })
        .catch(function (error) {
            // Обработка ошибок
            console.log(error);
        });
    fetch('/admin/getCustomAgency', {
        method: 'GET'
    }).then(res => res.json())
        .then(data => {
            var xValues = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110];
            var yValues = data;

            new Chart("newChart", {
                type: "line",
                data: {
                    labels: xValues,
                    datasets: [{
                        label: "Оценки качества оказания таможеннх услуг",
                        backgroundColor: "#E6EE9C",
                        borderColor: "#E6EE9C",
                        data: yValues,
                    }]
                }
               });

        });
});

