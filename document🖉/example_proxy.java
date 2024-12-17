// 1.

Mosaic_AllMethodsPublicProxy proxy = new Mosaic_AllMethodsPublicProxy(SomeClass.class);

String methodName = "compute";
Class<?> returnType = int.class;
Object[] args = {42, 15};

Object result = proxy.invoke(someInstance, methodName, returnType, args);
System.out.println(result);


// 2.

Method method = SomeClass.class.getDeclaredMethod("compute", int.class, int.class);
FunctionSignature sigFromReflection = new FunctionSignature(method);

FunctionSignature sigFromInvocation = new FunctionSignature(
  "com.example.SomeClass",
  "compute",
  int.class,
  new Object[]{42, 15}
);

System.out.println(sigFromReflection.equals(sigFromInvocation)); // Should be true

