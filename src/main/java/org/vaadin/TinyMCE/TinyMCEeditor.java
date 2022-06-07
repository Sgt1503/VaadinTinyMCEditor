package org.vaadin.TinyMCE;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.internal.PendingJavaScriptInvocation;
import com.vaadin.flow.component.internal.UIInternals;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.internal.StateNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Queue;

/**
 * @author Sergey.Tolstykh
 * @version 1.0
 *          Date 30.05.2022
 */

@Tag("tinymce-editor")
@NpmPackage(value = "@tinymce/tinymce-webcomponent",version = "2.0.0")
@JsModule("./src/tinymce-loader.js")
public class TinyMCEeditor<T extends Object> extends CustomField<T> {
  private T t;
  private String id;
  private String elem;
  private String innerHTML;
  private boolean isInit = false;
  private Queue<String> queueOfStringsToAppend;
  private String setStringBeforeInit;
  private String setStringTagBeforeInit;
  private SerializableConsumer<String> serializableConsumer;

  public TinyMCEeditor(String apikey, String innerHTML,String height, String menubar, String plugins, String toolbar, String contentStyle, SerializableConsumer<String> serializableConsumer, T t) {
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
    if (serializableConsumer != null)
      this.serializableConsumer = serializableConsumer;
    if (t != null)
      this.t = t;
    getElement().setAttribute("id", "text-editor");
    id = getElement().getAttribute("id");
    elem = "document.getElementById('" + id + "')";
    addTextChangeListener();
    addEventOnInit();
  }

  public TinyMCEeditor(String apikey, String innerHTML,String height, String menubar, String plugins, String toolbar, String contentStyle, SerializableConsumer<String> serializableConsumer) {
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
    if (serializableConsumer != null) {
      this.serializableConsumer = serializableConsumer;
    }
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
  protected T generateModelValue() {
    return getValue();
  }

  @Override
  protected void setPresentationValue(T o) {
    super.setValue(o);
  }

  @Override
  public T getValue() {
    return super.getValue();
  }

  @Override
  public void setValue(Object value) {
    super.setValue((T) value);
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
            "    tinymce.activeEditor.setContent('" + getValidExpressionForInsert(innerHTML) + "');\n" +
            "    elem.dispatchEvent(new Event('FieldInit'));\n" +
            "});\n" +
            "\n"
    );
    if (isTypeString())
      addSetter();
    else
      addSetter(serializableConsumer);
  }

  boolean isTypeString(){
    if (t == null)
      return true;
    return ((Class) t).getSimpleName().equals("String");
  }

  protected void addSetter() {
    getElement().addEventListener("Change", l -> {
      getJavaScriptReturn(this.getElement().getNode(),
              elem +
                      "._editor.getContent()")
              .then(String.class, s -> setValue(s));
    });
  }
  protected void addSetter(SerializableConsumer<String> serializableConsumer) {
    if (serializableConsumer == null) {
      serializableConsumer = (s)-> {throw new NullPointerException("No value consumer presented");};
    }
    SerializableConsumer<String> finalSerializableConsumer = serializableConsumer;
    getElement().addEventListener("Change", l -> {
      getJavaScriptReturn(this.getElement().getNode(),
              elem +
                      "._editor.getContent()")
              .then(String.class,
                      /*In this place you should send Consumer of String data from client
                      *that will make type conversion from your generic T to String
                      * and than make setValue(yourValue)
                      * can be done as a simple lambda
                      * s -> {
                      *   T value = doSomth(s);
                      *   setValue(value)}
                      * */
                      finalSerializableConsumer);
    });
  }

  public void setStrValue(String strValue, String tag) {
    if (!isInit) {
      //In case if server invoke method before client's side editor initialized
      setStringBeforeInit = strValue;
      setStringTagBeforeInit = tag;
    }
    if (isInit && (setStringTagBeforeInit != null || strValue.equals(setStringBeforeInit))) {
      removeContent();
      getJavaScriptInvoke(this.getElement().getNode(),
              "tinymce.activeEditor.setContent('<"+ tag +">" + setStringBeforeInit +  "</" + tag + ">');"
      );
    }
    if (isInit && strValue != null && !strValue.equals(setStringBeforeInit)) {
      removeContent();
      getJavaScriptInvoke(this.getElement().getNode(),
              "tinymce.activeEditor.setContent('<"+ tag +">" +  strValue +  "</" + tag + ">');"
      );
    }
  }

  public PendingJavaScriptInvocation removeContent() {
    setValue("");
    return getJavaScriptInvoke(this.getElement().getNode(),
            "tinymce.activeEditor.resetContent()"
    );
  }

  public void appendStrValue(String strValue, String tag) {
    String oldValue = (String) getValue();
    if (!isInit) {
      //In case if server invoke method before client's side editor initialized
      queueOfStringsToAppend.add(strValue);
    }
    if (getValue() == null)
      return;
    if (isInit) {
      if (queueOfStringsToAppend != null) {
        removeContent();
        String expression = "tinymce.activeEditor.insertContent('" + getValidExpressionForInsert(oldValue) + "<" + tag + ">"  + queueOfStringsToAppend.peek() + "</" + tag + ">" + "');";
        getJavaScriptInvoke(this.getElement().getNode(),
                expression
        );
      }
      if (queueOfStringsToAppend == null) {
        removeContent();
        String expression = "tinymce.activeEditor.insertContent('" + getValidExpressionForInsert(oldValue) + "<" + tag + ">" +  strValue + "</" + tag + ">" + "');";
        getJavaScriptInvoke(this.getElement().getNode(),
                expression
        );
      }
    }
  }

  public String getValidExpressionForInsert(String value) {
    return StringUtils.replace(StringUtils.remove((!StringUtils.isEmpty(value) ? value : ""), "\n"), "'", "");
  }


  private void addEventOnInit() {
    getElement().addEventListener("FieldInit", l-> {
      isInit = true;
      //In case if server invoke method before client's side editor initialized
      if (setStringBeforeInit != null && setStringTagBeforeInit != null)
        setStrValue(setStringBeforeInit, setStringTagBeforeInit);
      if (queueOfStringsToAppend != null)
        while (!queueOfStringsToAppend.isEmpty())
          appendStrValue(queueOfStringsToAppend.peek(), setStringTagBeforeInit);
    });
  }

  public boolean isInit() {
    return isInit;
  }
}