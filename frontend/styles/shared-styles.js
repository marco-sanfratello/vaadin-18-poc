// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
<style include='lumo-badge'>
        html {
      --lumo-border-radius: 0px;

    }

</style>
</custom-style>


`;

document.head.appendChild($_documentContainer.content);
