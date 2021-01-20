package com.mycompany.build
class Foo {

  Foo() {
  }

  public void runBar() {
    Bar bar = new Bar();
    bar.name = "my name";
    System.out.println(bar.name);
  }

  static void main(String[] args) {
    Foo foo = new Foo();
    foo.runBar()
  }
}
