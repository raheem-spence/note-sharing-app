export async function loadNotes(courseId) {
    try {
        // 1. send the network request
        const response = await fetch(`http://127.0.0.1:8080/courses/${courseId}/notes`, {
            method: 'GET',
            credentials: 'include'
        });

        // 2. check if response status is OK
        if (!response.ok) {
            throw new Error(`Http error! Status: ${response.status}`);
        }

        // 3. parse the stream data into a JSON object
        const notes = await response.json();
        return notes;
    } catch (error) {
        // catches network errors or errors thrown above
        console.error('Fetch failed:', error);
    }
}


export async function createNote(courseId, noteData) {
    try {
        const response = await fetch(`http://127.0.0.1:8080/courses/${courseId}/notes`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(noteData) // converts JS object into a JSON string
        });

        if(!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

    } catch (error) {
        console.log('Error:', error);
    } 
}