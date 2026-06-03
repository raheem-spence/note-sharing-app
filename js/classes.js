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
