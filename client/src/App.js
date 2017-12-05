import React, { Component } from 'react';
import LexChat from 'react-lex'

class App extends Component {
    render() {
        return (

            <LexChat
                botName="Recipe"
                IdentityPoolId="<put your bot's identity pool id here>"
                placeholder="Placeholder text"
                style={{position: 'absolute'}}
                backgroundColor="#FFFFFF"
                height={430}
                headerText="Chat with our awesome bot" />
        )
    }
}
export default App;
