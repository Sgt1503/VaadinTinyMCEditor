package org.vaadin.TinyMCE;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.internal.PendingJavaScriptInvocation;
import com.vaadin.flow.component.internal.UIInternals;
import com.vaadin.flow.internal.StateNode;

import java.util.Queue;

/**
 * @author Sergey.Tolstykh
 * @version 1.0
 *          Date 30.05.2022
 */

@Tag("tinymce-editor")
@NpmPackage(value = "@tinymce/tinymce-webcomponent",version = "2.0.0")
@JsModule("./src/tinymce-loader.js")
public class TinyMCEeditor extends CustomField<String> {
  private String id;
  private String elem;
  private String innerHTML;
  private boolean isInit = false;
  private Queue<String> queueOfStringsToAppend;
  private String setStringBeforeInit;

  public TinyMCEeditor(String apikey, String innerHTML,String height, String menubar, String plugins, String toolbar, String contentStyle) {
    setApiKey(apikey != null ? apikey : "no-api-key");
    if (height != null)
      setEditorHeight(height);
    if (menubar != null)
      setMenubar(menubar);
    if (plugins != null)
      setPlugins(plugins);
    if (toolbar != null)
      setToolbar(toolbar);
    if (contentStyle != null)
      setContentStyle(contentStyle);
    if (innerHTML != null)
      this.innerHTML = innerHTML;
    getElement().setAttribute("id", "text-editor");
    id = getElement().getAttribute("id");
    elem = "document.getElementById('" + id + "')";
    addTextChangeListener();
    addEventOnInit();
  }

  @Override
  public void setId(String id) {
    super.setId(id);
    getElement().setAttribute("id", "text-editor");
    id = getElement().getAttribute("id");
    elem = "document.getElementById('" + id + "')";
    addTextChangeListener();
  }

  private void setContentStyle(String s) {
    this.getElement().setAttribute( "content_style",
            s);
  }

  private void setToolbar(String args) {
    this.getElement().setAttribute( "toolbar", args);
  }

  private void setPlugins(String args) {
    this.getElement().setAttribute( "plugins",args);
  }

  private void setMenubar(String args) {
    this.getElement().setAttribute( "menubar", args.equals("false") ? "false" : args);
  }

  private void setEditorHeight(String height) {
    this.getElement().setAttribute( "height", height);
  }

  private void setApiKey(String apikey) {
    this.getElement().setAttribute("api-key", apikey);
  }

  @Override
  protected String generateModelValue() {
    return getElement().getText();
  }

  @Override
  protected void setPresentationValue(String s) {
    super.setValue(s);
  }

  @Override
  public String getValue() {
    return super.getValue();
  }

  @Override
  public void setValue(String value) {
    super.setValue(value);
  }



  private PendingJavaScriptInvocation getJavaScriptReturn(StateNode node, String expression) {
    UIInternals.JavaScriptInvocation invocation = new UIInternals.JavaScriptInvocation("return " + expression);
    PendingJavaScriptInvocation pending = new PendingJavaScriptInvocation(node, invocation);
    node.runWhenAttached((ui) -> {
      ui.getInternals().getStateTree().beforeClientResponse(node, (context) -> {
        if (!pending.isCanceled()) {
          context.getUI().getInternals().addJavaScriptInvocation(pending);
        }
      });
    });
    return pending;
  }

  private PendingJavaScriptInvocation getJavaScriptInvoke(StateNode node, String expression) {
    UIInternals.JavaScriptInvocation invocation = new UIInternals.JavaScriptInvocation(expression);
    PendingJavaScriptInvocation pending = new PendingJavaScriptInvocation(node, invocation);
    node.runWhenAttached((ui) -> {
      ui.getInternals().getStateTree().beforeClientResponse(node, (context) -> {
        if (!pending.isCanceled()) {
          context.getUI().getInternals().addJavaScriptInvocation(pending);
        }
      });
    });
    return pending;
  }
  public void addTextChangeListener() {
    getJavaScriptInvoke(this.getElement().getNode(),
    "let elem = document.getElementById('text-editor');\n" +
            "let iframe;\n" +
            "let conDoc;\n" +
            "\n" +
            "\n" +
            "async function waitForTiny() {\n" +
            "    while (typeof tinymce !== \"object\") {\n" +
            "       await sleep(1000); \n" +
            "    }\n" +
            "    iframe = elem.shadowRoot.querySelector('iframe')\n" +
            "    while (typeof iframe !== \"object\"){\n" +
            "        await sleep(1000)\n" +
            "    }\n" +
            "    while (!!!tinymce.activeEditor.getBody()){\n" +
            "        await sleep(1000)\n" +
            "    }\n" +
            "    conDoc = tinymce.activeEditor.getBody();\n" +
            "    return tinymce;\n" +
            "}\n" +
            "\n" +
            "async function sleep(ms) {\n" +
            "    return new Promise(resolve => setTimeout(resolve, ms));\n" +
            "}\n" +
            "\n" +
            "function setupEditor() {\n" +
            "    let observer = new MutationObserver(mutations  => {\n" +
            "        elem.dispatchEvent(new Event('Change'));\n" +
            "    });\n" +
            "    observer.observe(conDoc, {\n" +
            "    childList: true, // наблюдать за непосредственными детьми\n" +
            "    subtree: true, // и более глубокими потомками\n" +
            "    characterDataOldValue: true // передавать старое значение в колбэк\n" +
            "    });\n" +
            "}\n" +
            "\n" +
            "\n" +
            "\n" +
            "waitForTiny().then(()=>{\n" +
            "    setupEditor();\n" +
            "    iframe.contentDocument.body.innerHTML =  '" + innerHTML + "';\n" +
            "    elem.dispatchEvent(new Event('FieldInit'));\n" +
            "});\n" +
            "\n"
    );

    getElement().addEventListener("Change", l -> {
      getJavaScriptReturn(this.getElement().getNode(),
              elem +
                      "._editor.getContent()")
              .then(String.class, this::setValue);
    });
  }

  public void setStrValue(String strValue) {
    if (!isInit) {
      //In case if server invoke method before client's side editor initialized
      setStringBeforeInit = strValue;
    }
    if (isInit) {
      getJavaScriptInvoke(this.getElement().getNode(),
              elem + ".shadowRoot.querySelector('iframe').contentDocument.body.innerHTML =  '" + setStringBeforeInit != null ? setStringBeforeInit : strValue +  "';"
      );
    }
  }

  //TODO: value puts to top sometimes
  public void appendStrValue(String strValue) {
    if (!isInit) {
      //In case if server invoke method before client's side editor initialized
      queueOfStringsToAppend.add(strValue);
    }
    if (getValue() == null)
      return;
    if (isInit)
      getJavaScriptInvoke(this.getElement().getNode(),
              "tinymce.activeEditor.selection.setContent('" +"<p>" + queueOfStringsToAppend != null ? queueOfStringsToAppend.peek() : strValue + "</p>" + "');"
      );
  }

  private void addEventOnInit() {
    getElement().addEventListener("FieldInit", l-> {
      isInit = true;
      //In case if server invoke method before client's side editor initialized
      if (setStringBeforeInit != null)
        setStrValue(setStringBeforeInit);
      if (queueOfStringsToAppend != null)
        while (!queueOfStringsToAppend.isEmpty())
          appendStrValue(queueOfStringsToAppend.peek());
    });
  }

  public boolean isInit() {
    return isInit;
  }
}