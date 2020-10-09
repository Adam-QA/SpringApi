const params = new URLSearchParams(window.location.search);

for(let param of params){
    let id = param[1];
    getSingleRecord(id); 
}

function getSingleRecord(id){
fetch('http://localhost:8901/tasklist/read/' + id)
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      response.json().then(function(taskdata) {
        console.log(taskdata);
        
        document.getElementById("inputID").value=taskdata.id;
        document.getElementById("inputName").value=taskdata.name;
        
        let table = document.querySelector("table");
        let data = Object.keys(taskdata);

        // newdata = taskdata.tasks[0].toDo;
        // console.log(newdata);
        // console.log(data);
        // (taskdata.tasks.forEach(function(message){
        //   console.log(message);
        // }))
        
        

        createTableHead(table, data);
        createTableBody(table, taskdata);

        

        
        

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
    let taskListName = formElement["inputName"].value;
    
    updateBand(id, taskListName);

})

function updateBand(id, taskListName){
    let updateID = parseInt(id);



    fetch("http://localhost:8901/tasklist/update/" + updateID, {
        method: 'put',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "name": taskListName
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


function createTableHead(table, data){
  let tableHead = table.createTHead();
  let row = tableHead.insertRow();

  for(let keys of data){
    if(keys == "name"){

    }else{
    
    
      let th = document.createElement("th");
      let text = document.createTextNode(keys);
      th.appendChild(text);
      row.appendChild(th);
  }}
  

}


function createTableBody(table, taskdata){
  for(let key in taskdata){
    if(key == "tasks"){
      let arr = taskdata[key];
      for(let i = 0; i < arr.length; i++){
        let obj = arr[i];
        console.log(obj);
        let row = table.insertRow();
        
        for(let prop in obj){
          console.log(prop);
          console.log(obj[prop]);
          let cell = row.insertCell();
          let text = document.createTextNode(obj[prop]);
          cell.appendChild(text);
          if(prop == "id"){
            var deleteid = obj[prop];
          }
        }
        let editcell = row.insertCell();
        let myedit = document.createElement("a");
        myedit.className = "btn btn-info";
        myedit.innerHTML = "Edit";
        myedit.href = "edit.html?id=" + deleteid;

        editcell.appendChild(myedit);

        
        let deletecell = row.insertCell();
        let mydelete = document.createElement("button");
        mydelete.className = "btn btn-danger";
        mydelete.innerHTML = "Remove";
        
        mydelete.onclick = function(){
          deletetask(deleteid);return false;
        }

        deletecell.appendChild(mydelete);
        
      }
        

    }
    //   let row = table.insertRow();
    //   let count = 0;
    //   for(let info in taskRecord){
    //     count++;

    //     let cell = row.insertCell();
    //     if(count === 0){
    //       for(let tasks of taskRecord["tasks"]){
    //         let text = document.createTextNode(tasks.toDo);
    //         cell.appendChild(text);
    //       }
    //     }
    //     else{
    //     let text = document.createTextNode(taskRecord[info]);
    //     cell.appendChild(text);
    //   }
    // }

    //   let newCell2 = row.insertCell();
    //   let mydelete = document.createElement("button");
    //   mydelete.className = "btn btn-danger";
    //   mydelete.innerHTML = "Remove";
    //   mydelete.onclick = function(){
    //     deleteList(taskRecord.id);return false;
    //   }
    //   newCell2.appendChild(mydelete);
  }
}


document.querySelector("form.myNewTitle").addEventListener("submit", function(stop){
  stop.preventDefault();

  let name = document.querySelector("form.myNewTitle").elements;
  let title= name["abc"].value;
  for(let param of params){
    let id = param[1]; 
    addList(title, id);
}
  
  

})


function addList(name, id){
  pid = parseInt(id);

  fetch("http://localhost:8901/task/create", {
    method: 'post',
    headers: {
      "Content-type": "application/json"
    },
    body: json = JSON.stringify({
        "toDo": name,
        "tasklist":{
          "id": pid
        }
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

function deletetask(id){
  let deleteId = parseInt(id);

  fetch("http://localhost:8901/task/delete/" + deleteId, {
    method: 'delete',
    headers: {
      "Content-type": "application/json"
    },
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