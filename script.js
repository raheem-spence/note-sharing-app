// Class objects
const classes = [
    {id: "chem", name: "CHEM 101", description: "class about Chemistry"},
    {id: "bio", name: "BIO 101", description: "class about Biology"},
    {id: "physics", name: "PHYSICS 101", description: "class about Physics"},
    {id: "spanish", name: "SPANISH 101", description: "class about Spanish"}
]

const notes = [
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

// Get class object
const clsObj = classes.find(cls => cls.id === propVal);

// Get all note objects for specific class
const noteObjs = notes.filter(note => note.classId === propVal);

// Create H1 element
const heading = document.createElement('h1');

// Create paragraphe element
const para = document.createElement('p');

// Create notes cotainaer
const notesContainer = document.createElement('div');


// Create paragraph for each note
for (const note of noteObjs) {
    const noteContent = document.createElement('p');
    noteContent.classList.add('note');
    noteContent.textContent = note.text;
    notesContainer.appendChild(noteContent);
}


// Fill elements with class info
heading.textContent = clsObj.name;
para.textContent = clsObj.description;

heading.classList.add('class-heading');
para.classList.add('class-para');
notesContainer.classList.add('notesContainer');

document.body.appendChild(heading);
document.body.appendChild(para);
document.body.appendChild(notesContainer);
