import random

from django.http import JsonResponse

from kebabmania.models import Usuario, Opiniones, Kebab, Ciudad


def home(request):
    request_token = request.META.get('HTTP_SESSION_TOKEN')

    if request_token is None:
        return JsonResponse({"error": "Unauthorized"}, status=401)
    try:
        user = Usuario.objects.get(token=request_token)
    except Usuario.DoesNotExist:
        return JsonResponse({"error": "Unauthorized"}, status=401)

    if request.method != "GET":
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)


    last_opinion = Opiniones.objects.all().order_by('-id')[:1]
    json_opinion = []
    if last_opinion is not None:

        json_last_opinion = last_opinion.to_json()

        json_last_opinion_json = json_last_opinion.to_json()
        kebab_nombre_opinion = Kebab.objects.get(id=json_last_opinion_json['id_kebab'])
        kebab_nombre_opinion_json = kebab_nombre_opinion.to_json()
        json_opinion['nombreKebab'] = kebab_nombre_opinion_json['nombre']
        json_opinion['nota'] = str (json_last_opinion_json['nota'])
        usuario_nombre_opinion = Usuario.objects.get(id=json_last_opinion_json['id_usuario'])
        usuario_nombre_opinion_json = usuario_nombre_opinion.to_json()

        json_opinion['nombreUsuario'] = usuario_nombre_opinion_json['nombre']



    else:
        json_opinion['nombreKebab'] = 'Unknown'
        json_opinion['nota'] = '0'
        json_opinion['nombreUsuario'] = 'Unknown'


    json_response = []

    json_response.append(json_opinion)


    all_kebab = Kebab.objects.all()

    random_number = random.randint(0, len(all_kebab))


    random_kebab = Kebab.objects.get(id=random_number)

    random_kebab_json = random_kebab.to_json()

    ciudad = Ciudad.objects.get(id=random_kebab_json['id_ciudad'])
    ciudad_json = ciudad.to_json()
    random_kebab_json['nom_ciudad'] = ciudad_json['nom_ciudad']


    json_response.append(random_kebab_json)

    return JsonResponse(json_response, safe=False)
