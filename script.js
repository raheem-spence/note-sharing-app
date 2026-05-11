// Class objects
const classes = [
    {id: "chem", name: "CHEM 101", description: "class about Chemistry"},
    {id: "bio", name: "BIO 101", description: "class about Biology"},
    {id: "physics", name: "PHYSICS 101", description: "class about Physics"},
    {id: "spanish", name: "SPANISH 101", description: "class about Spanish"}
]

let notes = [
    {id: 1, classId: "chem", text: "Today we learned about organic compounds and how they interact with one another"},
    {id: 2, classId: "bio", text: "For this lecture, we discussed anatomy."},
    {id: 3, classId: "chem", text: "We learned about chelation today"},
    {id: 4, classId: "bio", text: "We learned about cell structure of prokaryote/eukaryote, membrane transport, photosynthesism, cellular respiration."},
    {id: 5, classId: "bio", text: "Today's topic was Genetics and Heredity: DNA structure, replication, protein synthesis (transcription/translation), Mendelian genetics, molecular genetics, and gene regulation. "},
    {id: 6, classId: "spanish", text: "Learned about the subjunctive and phrases that trigger the subjunctive. For example, 'para que', 'sin que'."},
    {id: 7, classId: "physics", text: "This class we learned about torque and angular momentum."}
]

// Redirect to classes page
// Select button
const loginBtn = document.getElementById('login-btn');

// Check if element exists, meaning we are on that page
if (loginBtn) {
    // Add listener
    loginBtn.addEventListener('click', event => {
        window.location.href = "classes.html";
    })
}

// CLASSES PAGE
// Select classes container
const classesContainer = document.getElementById('classes-container');

// Check if element exist, meaning we are on that page
if (classesContainer) {
    // Add listener to classes container
    classesContainer.addEventListener('click', event => {
       
        // Get parent card
        const parentCard = event.target.closest('.class-card');

        // Class card id
        const classId = parentCard.id;

        // Navigate to page
        window.location.href = `class.html#${classId}`;
    })
} 



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
renderNotes(propVal);

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
createBtn.addEventListener('click', event => {
    const newNoteContent = noteTextarea.value;

    // Validate input
    const valid = validateInput(newNoteContent);

    if (!valid) {
        return;
    }

    // Create new note object
    // Object ID
    const objId = Date.now();

    // Class ID
    const clsId = propVal;

    const newNoteObj = {id: objId, classId: clsId, text: newNoteContent};

    // Push new note object to notes array
    notes.push(newNoteObj);


    // Clear input
    noteTextarea.value = "";

    updateNotes(propVal);
})


// Render function to remove duplicate logic for rendeing notes
function renderNotes(classId) {

    // Clear all notes
    notesContainer.replaceChildren();

    // Get class object
    const clsObj = classes.find(cls => cls.id === classId);

    // Get all note objects for specific class
    const noteObjs = notes.filter(note => note.classId === classId);

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
    // Get notes from local storage
    const jsonNotes = localStorage.getItem('notes');

    if (jsonNotes === null) {
        notes = [];
    } else {
        // Convert JSON string of notes into an object
        notes = JSON.parse(jsonNotes);
    }
}


// Update notes function responsible for the update cycle
function updateNotes(propVal) {
    saveNotes();
    renderNotes(propVal);
}