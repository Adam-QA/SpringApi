const params = new URLSearchParams(window.location.search);

for(let param of params){
    let id = param[1];
    getSingleRecord(id); 
}

function getSingleRecord(id){
    fetch('http://localhost:8901/task/read/' + id)
      .then(
        function(response) {
          if (response.status !== 200) {
            console.log('Looks like there was a problem. Status Code: ' +
              response.status);
            return;
          }
    
          response.json().then(function(data) {
            console.log(data);
            for(let keys in data){
                console.log(data[keys]);
                if(keys == "id"){
                    var editid = data[keys];
                }
                if(keys == "toDo"){
                  var edittask = data[keys];
                    
            }}
            
            document.getElementById("inputID").value=editid;
            document.getElementById("inputName").value=edittask;
            
            // let table = document.querySelector("table");
            // let data = Object.keys(taskdata);
    
            // newdata = taskdata.tasks[0].toDo;
            // console.log(newdata);
            // console.log(data);
            // (taskdata.tasks.forEach(function(message){
            //   console.log(message);
            // }))
            
            
    
            // createTableHead(table, data);
            // createTableBody(table, taskdata);
    
            
    
            
            
    
          // Examine the text in the response
          
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }

    document.querySelector("form.taskListRecord").addEventListener("submit", function(stop){
      stop.preventDefault();
  
      let formElement = document.querySelector("form.taskListRecord").elements;
      let id = formElement["inputID"].value;
      let taskname = formElement["inputName"].value;
      
      updatetask(id, taskname);
  
  })


  function updatetask(id, taskname){
    let updateID = parseInt(id);



    fetch("http://localhost:8901/task/update/" + updateID, {
        method: 'put',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "toDo": taskname
        })
      })
      // .then(res => res.json())
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
        refresh();
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
    
    
    
}

function refresh(){
  location.reload();
}