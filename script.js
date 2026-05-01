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

        // ID to attach to button for deleting/editing notes by id
        deleteBtn.dataset.id = noteId;
        editBtn.dataset.id = noteId;

        

        // Convert array into a string
        jsonNotes = JSON.stringify(allNotes);

        // Save notes in local storage
        localStorage.setItem('notesArray', jsonNotes);

        // Add a CSS class
        newNote.classList.add("note");
        newContainer.classList.add("divPara");
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
    editBtn.dataset.id = loadedNote.id;



    // Nest elements
    oldContainer.appendChild(oldContent);
    btnContainer.appendChild(editBtn);
    btnContainer.appendChild(deleteBtn);
    oldNote.appendChild(oldContainer);
    oldNote.appendChild(btnContainer);

    // Add a CSS class
    oldNote.classList.add("note");
    oldContainer.classList.add("divPara");
    btnContainer.classList.add("btn-container");

    // Add each old note to the notes container
    notes.appendChild(oldNote);

}




// UPDATE / DELETE NOTES
notes.addEventListener("click", event => {

    // Delete note
    if (event.target.tagName === 'BUTTON' && event.target.classList.contains('deleteBtn')) {
        let noteId = event.target.dataset.id;

        const updatedNotes = allNotes.filter(currNote => currNote.id !== Number(noteId));
        allNotes = updatedNotes;

         // Convert array into a string
         jsonNotes = JSON.stringify(allNotes);
    
         // Save notes in local storage
         localStorage.setItem('notesArray', jsonNotes);
         event.target.parentElement.parentElement.remove();

    // Update note
    } else if (event.target.tagName === 'BUTTON' && event.target.classList.contains('editBtn')) {
        let noteId = event.target.dataset.id;

        // Get article element (parent of the buttons parent)
        const noteContainer = event.target.parentElement.parentElement;

        // Get paragraph element
        const paraElement = noteContainer.querySelector('p');

        // Capture original text to prefill input box
        const paraOriginal = paraElement.textContent;

        // Remove paragraph element
        paraElement.remove();

        // Create input element
        const editBox = document.createElement('input');

        // Prefill input box with original paragraph text
        editBox.value = paraOriginal;

        // Prepend input element, place it at he very start of the parent (article) container
        noteContainer.prepend(editBox);

        // Save the edit
        editBox.addEventListener("keydown", event => {
            if (event.key === "Enter") {
                // Get value from input box
                const editValue = editBox.value;
                
                // Remove input box
                editBox.remove();
                
                // Create paragraph element 
                const editContent = document.createElement('p');

                // Add value from input to paragraph element
                editContent.textContent = editValue;

                // Get div from note container (parent)
                const divPara = noteContainer.querySelector('.divPara');

                // Add edited paragraph to the beginning of the div container
                divPara.prepend(editContent);


                // SAVE EDIT
                // Get the note via id from edit button
                const editedNote = allNotes.find(note => note.id === Number(noteId));

                // Set edited value in note
                editedNote.text = editValue;

                // Save to all notes
                // Convert array into a string
                jsonNotes = JSON.stringify(allNotes);
           
                // Save notes in local storage
                localStorage.setItem('notesArray', jsonNotes);

            }
        })

    }
});

