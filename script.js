// Select text element by their ids
const myTextBox = document.getElementById("myTextBox");
const notes = document.getElementById("notes-container");


let allNotes;

// CREATE NOTES
myTextBox.addEventListener("keydown", event => {
    // Check if the pressed key is 'Enter'
    if (event.key === "Enter") {
        // Get value from textbox
        const note = myTextBox.value;

        // Create a new article element
        const newNote = document.createElement('article');
        const newContainer = document.createElement('div');
        const btnContainer = document.createElement('div');
        const newContent = document.createElement('p');


        // Edit button
        const editBtn = document.createElement('button');
        editBtn.textContent = "Edit"
        editBtn.classList.add("editBtn");
        
        
        // Delete button
        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = "Delete"
        deleteBtn.classList.add("deleteBtn");

    
        // Add text content to article element
        newContent.textContent = note;

        // Nest elements
        newContainer.appendChild(newContent);
        btnContainer.appendChild(editBtn);
        btnContainer.appendChild(deleteBtn);
        newNote.appendChild(newContainer);
        newNote.appendChild(btnContainer);

        // Add new note to the array of notes
        let noteId = Date.now()
        allNotes.push({id: noteId, text: note});

        // ID to attach to button for deleting notes by id
        deleteBtn.dataset.id = noteId;

        

        // Convert array into a string
        jsonNotes = JSON.stringify(allNotes);

        // Save notes in local storage
        localStorage.setItem('notesArray', jsonNotes);

        // Add a CSS class
        newNote.classList.add("note");
        btnContainer.classList.add("btn-container");

        // Returns an HTMLCollection of all <article> elements
        const articleElements = notes.getElementsByTagName("article");

        // Access the first one
        const firstArticleElement = articleElements[0];

        notes.insertBefore(newNote, firstArticleElement);

        myTextBox.value = "";
    }
});


// READ NOTES

// Get array of notes from local storage
const allJsonNotes = localStorage.getItem('notesArray');

if (allJsonNotes === null) {
    allNotes = [];
} else {
    // Deserialize JSON string into an array
    allNotes = JSON.parse(allJsonNotes);
}

let jsonNotes;


// Display notes from local storage
for (let loadedNote of allNotes) {
    let oldNote = document.createElement('article');
    let oldContainer = document.createElement('div');
    let btnContainer = document.createElement('div');
    let oldContent = document.createElement('p');

    oldContent.textContent = loadedNote.text;

    // Delete button
    const deleteBtn = document.createElement('button');
    deleteBtn.textContent = "Delete"
    deleteBtn.classList.add("deleteBtn");
    deleteBtn.dataset.id = loadedNote.id;


    // Edit button
    const editBtn = document.createElement('button');
    editBtn.textContent = "Edit";
    editBtn.classList.add("editBtn");



    // Nest elements
    oldContainer.appendChild(oldContent);
    btnContainer.appendChild(editBtn);
    btnContainer.appendChild(deleteBtn);
    oldNote.appendChild(oldContainer);
    oldNote.appendChild(btnContainer);

    // Add a CSS class
    oldNote.classList.add("note");
    btnContainer.classList.add("btn-container");

    // Add each old note to the notes container
    notes.appendChild(oldNote);

}


// DELETE NOTES
notes.addEventListener("click", event => {
    if (event.target.tagName === 'BUTTON' && event.target.classList.contains('deleteBtn')) {
        let noteId = event.target.dataset.id;

        const updatedNotes = allNotes.filter(currNote => currNote.id !== Number(noteId));
        allNotes = updatedNotes;

         // Convert array into a string
         jsonNotes = JSON.stringify(allNotes);
    
         // Save notes in local storage
         localStorage.setItem('notesArray', jsonNotes);
         event.target.parentElement.parentElement.remove();

    }
});

