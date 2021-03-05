// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
    <style include='lumo-badge'>
        html {
            --lumo-border-radius: 0px;

            --lumo-primary-color: hsl(149,100%,37%);
            --lumo-primary-color-50pct: hsla(149,100%,37%, 0.5);
            --lumo-primary-color-10pct: hsla(149,100%,37%, 0.1);
            
            --_lumo-button-primary-background-color: var(--lumo-primary-color);
        }
        [theme~="dark"] {
            --lumo-primary-color: hsl(149,100%,37%);     
            --lumo-base-color: var(--lumo-primary-color);  
            --_lumo-button-color: white; /* menu color */
            /*--lumo-primary-text-color /* Currently set to a blueish color */
        }
    </style>
</custom-style>
`;

document.head.appendChild($_documentContainer.content);
