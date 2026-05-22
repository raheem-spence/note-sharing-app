// Class objects
const classes = [
    {id: "chem", name: "CHEM 101", description: "class about Chemistry"},
    {id: "bio", name: "BIO 101", description: "class about Biology"},
    {id: "physics", name: "PHYSICS 101", description: "class about Physics"},
    {id: "spanish", name: "SPANISH 101", description: "class about Spanish"}
]


// CLASS PAGE 
// Get specific class by hash (e.g. #chem)
// Remove '#' using substring()
const cleanedHash = window.location.hash.substring(1);
const propVal = String(cleanedHash);

// Create div element for textarea and new note button
const newNoteContainer = document.createElement('div');

// Create textarea element for note
const noteTextarea = document.createElement('textarea');
noteTextarea.cols = 65;
noteTextarea.placeholder = "Take a note..."

// Create button to create note
const createBtn = document.createElement('button');
createBtn.textContent = "Create"

// Create H1 element
const heading = document.createElement('h1');

// Create paragraphe element
const para = document.createElement('p');

// Create notes cotainaer
const notesContainer = document.createElement('div');

loadNotes();

// Add elements to their css classes
heading.classList.add('class-heading');
para.classList.add('class-para');
notesContainer.classList.add('notesContainer');
newNoteContainer.classList.add('new-note-container');
createBtn.classList.add('create-btn');

newNoteContainer.appendChild(noteTextarea);
newNoteContainer.appendChild(createBtn);

document.body.appendChild(heading);
document.body.appendChild(para);
document.body.appendChild(newNoteContainer);
document.body.appendChild(notesContainer);


// Create note
// Event listener for click of create btn
createBtn.addEventListener('click', function () {

    const newNoteContent = noteTextarea.value;

    // Validate input
    const valid = validateInput(newNoteContent);

    if (!valid) {
        return;
    }

    // Class ID
    const clsId = propVal;

    const noteData = {classId: clsId, text: newNoteContent};

    fetch('http://localhost:8080/api/v2/notes/create-note', {
        method: 'POST', // Specify the request method
        headers: {
            'Content-Type': 'application/json' // Inform the server im sending JSON
        },
        body: JSON.stringify(noteData) // Convert object into JSON string
    }).then(response => response.json()) // Parse JSON response
    .then(result => {
        console.log('note created:', result);
        loadNotes();
    }).catch(error => console.error('Error:', error));
    });



// Render function to remove duplicate logic for rendeing notes
function renderNotes(notes, classId) {

    // Clear all notes
    notesContainer.replaceChildren();

    // Get class object
    const clsObj = classes.find(cls => cls.id === classId);

    // Get all note objects for specific class
    const noteObjs = notes.filter(note => note.classId === classId);

    if (noteObjs.length === 0) {
        const noNotePara = document.createElement('p');
        noNotePara.textContent = "New notes appear here";
        noNotePara.classList.add('class-para');
        notesContainer.appendChild(noNotePara);    
    } else {

        // Create paragraph for each note
        for (const note of noteObjs) {
            const noteDiv = document.createElement('div');
            const noteContent = document.createElement('p');
            const btnContainer = document.createElement('div');
            const editBtn = document.createElement('button');
            const deleteBtn = document.createElement('button');
            editBtn.textContent = "Edit";
            deleteBtn.textContent = "Delete";


            // Attach note id to delete/edit button
            deleteBtn.dataset.id = note.id;
            editBtn.dataset.id = note.id;
    
            noteContent.textContent = note.text;
            btnContainer.appendChild(editBtn);
            btnContainer.appendChild(deleteBtn);
            noteDiv.appendChild(noteContent);
            noteDiv.appendChild(btnContainer);
        


            // Add to appropriate css class
            deleteBtn.classList.add('delete-btn');
            editBtn.classList.add('edit-btn');
            noteDiv.classList.add('note-div');
            noteContent.classList.add('note');

            notesContainer.appendChild(noteDiv);

        }
    }

    // Fill elements with class info
    heading.textContent = clsObj.name;
    para.textContent = clsObj.description;
}


// Event delegation of Notes Container
notesContainer.addEventListener('click', event => {

    // Delete note
    if (event.target.classList.contains('delete-btn')) {
        // Get note id via delete button id
        const noteId = event.target.dataset.id;

        // Remove the note object with specific note id
        notes = notes.filter(note => note.id !== Number(noteId));

        updateNotes(propVal);
    }

    // Update note
    else if (event.target.classList.contains('edit-btn')) {
        //Get note id via edit button id
        const noteId = event.target.dataset.id;

        // Get note to edit
        const note = notes.find(note => note.id === Number(noteId));

        // Find the note container
        const noteContainer = event.target.parentElement.parentElement;

        // Get the paragraph element needed to switch
        const para = noteContainer.querySelector('p');

        // Get the text area to swtich
        const editTextArea = noteContainer.querySelector('textarea');

        // Chekc if it contains a textarea
        if (editTextArea) {

            const newPara = document.createElement('p');
            newPara.classList.add('note');

            note.text = editTextArea.value;

            newPara.textContent = editTextArea.value;
            editTextArea.replaceWith(newPara);

            updateNotes(propVal);


        } else {
            // Textarea to edit
            const editArea = document.createElement('textarea');
            editArea.classList.add('edit-area')

            // Add current value to text area
            editArea.value = note.text;

            // Change edit button text to 'save'
            event.target.textContent = "Save";
            
            // Replace paragraph with edit area
            para.replaceWith(editArea);

            // Autofocus
            editArea.focus();
        }
    }
})


// HELPER FUNCTIONS

// Function to validate input when creating notes
function validateInput(text) {
    if (text.trim() === "") {
        alert("Invalid Input. Try again.");
        return false;
    } else {
        return true;
    }
}

// SAVE NOTES
function saveNotes() {
    // Convert notes array into a JSON string
    const jsonNotes = JSON.stringify(notes);

    // Save in localStorage
    localStorage.setItem('notes', jsonNotes);
}

// LOAD NOTES
function loadNotes() {

    // Get notes from backend
    fetch('http://localhost:8080/api/v2/notes')
    .then(response => response.json())
    .then(data => {
        notes = data;
        renderNotes(notes, propVal);
    })
}


// Update notes function responsible for the update cycle
function updateNotes(propVal) {
    saveNotes();
    renderNotes(notes, propVal);
}