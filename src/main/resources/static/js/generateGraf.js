document.addEventListener("DOMContentLoaded", function () {
    fetch("/admin/getListCountProduct", {
        method: 'GET'
    }).then(res => res.json())
        .then(data => {
            if (typeof data !== 'object' || data === null || Object.keys(data).length === 0) {
                console.error("Сервер вернул некорректные данные");
                return;
            }

            var labels = Object.keys(data);
            var values = Object.values(data);

            new Chart("newChart", {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "Асcортемент на складе",
                        backgroundColor: "#E6EE9C",
                        borderColor: "#E6EE9C",
                        data: values,
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return `${context.label}: ${context.formattedValue}`;
                                }
                            }
                        }
                    }]
                },
                options: {
                    plugins: {
                        tooltip: {
                            mode: 'index',
                            intersect: false
                        }
                    }
                }
            });
        })
        .catch(error => {
            console.error("Ошибка при получении данных:", error);
        });
});

