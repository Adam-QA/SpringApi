fetch('http://localhost:8901/tasklist/readAll')
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(taskData) {
        console.log(taskData);
        if(Object.keys == "tasks"){

        }else{

        

        

        let table = document.querySelector("table");
        let data = Object.keys(taskData[0]);

        // newElement(data);

        createTableHead(table, data);
        createTableBody(table, taskData);}
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });

  // let close = document.getElementsByClassName("close");
  // let i;
  // for(i = 0; i < close.length; i++){
  //   close[i].onclick = function(){
  //     let div = this.parentElement;
  //     div.style.display = "none";
  //   }
  // }


  function createTableHead(table, data){
      let tableHead = table.createTHead();
      let row = tableHead.insertRow();

      for(let keys of data){
        if(keys == "tasks"){

        }else{
        
          let th = document.createElement("th");
          let text = document.createTextNode(keys);
          th.appendChild(text);
          row.appendChild(th);
      }}
      let th2 = document.createElement("th");
      let text2 = document.createTextNode("View");
      th2.appendChild(text2);
      row.appendChild(th2);

  }

  function createTableBody(table, taskData){
      for(let taskRecord of taskData){
          let row = table.insertRow();
          for(values in taskRecord){
            if(values == "tasks"){

            }else{
              let cell = row.insertCell();
              let text = document.createTextNode(taskRecord[values]);
              cell.appendChild(text);
          }}
          

          let newCell = row.insertCell();
          let myButton = document.createElement("a");
          myButton.className = "btn btn-info";
          myButton.href= "record.html?id=" + taskRecord.id;
          myButton.innerHTML = "View";
          newCell.appendChild(myButton);

          let newCell2 = row.insertCell();
          let mydelete = document.createElement("button");
          mydelete.className = "btn btn-danger";
          mydelete.innerHTML = "Remove";
          mydelete.onclick = function(){
            deleteList(taskRecord.id);return false;
          }
          newCell2.appendChild(mydelete);
      }
  }

  // function newElement(){

    
  //   let li = document.createElement("li");
  //   let value = document.getElementById("myInput").value;
  //   let t = document.createTextNode(value);
  //   li.appendChild(t);
  //   if(value === ''){
  //     alert("You need to add something here!");
  //   }else{
  //     document.getElementById("myUL").appendChild(li);
  //   }
  //   document.getElementById("myInput").value = "";

  //   let span = document.createElement("SPAN");
  //   let text = document.createTextNode("\u00D7");
  //   span.className = "close";
  //   span.appendChild(text);
  //   li.appendChild(span);

  //   for (i = 0; i < close.length; i++) {
  //     close[i].onclick = function() {
  //       var div = this.parentElement;
  //       div.style.display = "none";
  //     }
  //   }

  // }

  function deleteList(id){
    let deleteId = parseInt(id);

    fetch("http://localhost:8901/tasklist/delete/" + deleteId, {
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

  function refresh(){
    location.reload();
  }

  function addList(name){
    fetch("http://localhost:8901/tasklist/create", {
      method: 'post',
      headers: {
        "Content-type": "application/json"
      },
      body: json = JSON.stringify({
          "name": name
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


  document.querySelector("form.myNewTitle").addEventListener("submit", function(stop){
    stop.preventDefault();

    let name = document.querySelector("form.myNewTitle").elements;
    let title= name["abc"].value;
    
    console.log(name)
    console.log(title)
    addList(title);

})
